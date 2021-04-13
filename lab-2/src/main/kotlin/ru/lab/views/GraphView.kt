package ru.lab.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Cursor
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.layout.Priority
import ru.lab.controllers.FormController
import ru.lab.controllers.FunctionController
import tornadofx.View
import tornadofx.hgrow
import tornadofx.linechart
import tornadofx.vbox
import tornadofx.vgrow

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
        hgrow = Priority.ALWAYS
        vgrow = Priority.ALWAYS

        linechart("Function graph", NumberAxis(), NumberAxis()) {
            hgrow = Priority.ALWAYS
            vgrow = Priority.ALWAYS

            isLegendVisible = false
            cursor = Cursor.CROSSHAIR
            setData(seriesList)
        }
    }
}