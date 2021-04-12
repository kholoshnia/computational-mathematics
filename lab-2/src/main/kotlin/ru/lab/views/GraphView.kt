package ru.lab.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import ru.lab.controllers.ComputeController
import ru.lab.controllers.FunctionController
import tornadofx.View
import tornadofx.linechart
import tornadofx.vbox

class GraphView : View() {
    private val formView: FormView by inject()
    private val functionController: FunctionController by inject()
    private val computeController: ComputeController by inject()
    private val seriesList: ObservableList<XYChart.Series<Number, Number>> = FXCollections.observableArrayList()

    init {
        renderGraph()
    }

    fun renderGraph() {
        val function = functionController.getFunction(formView.function.text)
        val left = formView.left.text.toDouble()
        val right = formView.right.text.toDouble()
        var accuracy = formView.accuracy.text.toDouble()
        if (accuracy < 0.01) accuracy = 0.01
        functionController.updateValues(seriesList, function, left, right, accuracy)

        //if (computeController.check(function, left, right)) {
            println("True")
            computeController.newton(function, left, right, accuracy)
            return
       // } else {
            println("False")
      //  }

    }

    override val root = vbox {
        linechart("Function graph", NumberAxis(), NumberAxis()) {
            isLegendVisible = false
            setData(seriesList)
        }
    }
}