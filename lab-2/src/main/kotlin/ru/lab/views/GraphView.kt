package ru.lab.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import ru.lab.services.FunctionService
import tornadofx.View
import tornadofx.linechart
import tornadofx.series
import tornadofx.vbox

class GraphView : View() {
    private val functionService = FunctionService()
    private val formView: FormView by inject()
    private val seriesList = FXCollections.observableArrayList<ObservableList<XYChart.Data<Number, Number>>>()

    fun renderGraph() {
        seriesList.clear()

        val function = formView.function.text
        val left = formView.left.text.toDouble()
        val right = formView.right.text.toDouble()
        var step = formView.step.text.toDouble()
        if (step < 0.1) step = 0.1

        var x = left
        val y = functionService.getFunction(function)
        var seriesNumber = 0

        while (x <= right) {
            val value = y(x)
            if (value.isNaN() || value.isInfinite()) {
                seriesNumber++
            }
            seriesList.getOrElse(seriesNumber) {
                seriesList.add(seriesNumber, FXCollections.observableArrayList())
                seriesList[seriesNumber]
            }.add(XYChart.Data(x, value))
            x += step
        }
    }


    override val root = vbox {
        linechart(null, NumberAxis(), NumberAxis()) {
            isLegendVisible = false
            seriesList.forEach {
                series("Function", elements = it)
            }
        }
    }
}