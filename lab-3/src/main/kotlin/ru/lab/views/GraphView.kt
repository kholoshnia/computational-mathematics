package ru.lab.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Cursor
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import ru.lab.controllers.FormController
import ru.lab.controllers.FunctionController
import tornadofx.View
import tornadofx.linechart
import tornadofx.vbox

class GraphView : View() {
    private val formController: FormController by inject()
    private val functionController: FunctionController by inject()
    private val seriesList: ObservableList<XYChart.Series<Number, Number>> = FXCollections.observableArrayList()

    init {
        renderGraph()
    }

    fun renderGraph() {
        functionController.updateValues(
            seriesList,
            formController.getFunction(),
            formController.getLeftBoundary(),
            formController.getRightBoundary(),
            formController.getStep()
        )
    }

    override val root = vbox {
        linechart("Function graph", NumberAxis(), NumberAxis()) {
            isLegendVisible = false
            cursor = Cursor.CROSSHAIR
            setData(seriesList)
        }
    }
}