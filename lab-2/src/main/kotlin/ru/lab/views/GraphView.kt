package ru.lab.views

import javafx.collections.FXCollections
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
    private val graph = FXCollections.observableArrayList<XYChart.Data<Number, Number>>()

    fun renderGraph() {
        graph.clear()

        val function = formView.function.text
        val left = formView.left.text.toDouble()
        val right = formView.right.text.toDouble()

        var step = formView.step.text.toDouble()
        if (step < 0.1) step = 0.1

        var x = left
        val y = functionService.getFunction(function)

        while (x <= right) {
            val value = y(x)
            if (!value.isNaN() && !value.isInfinite()) {
                graph.add(XYChart.Data(x, y(x)))
            }
            x += step
        }
    }


    override val root = vbox {
        linechart(null, NumberAxis(), NumberAxis()) {
            series("Function graph", elements = graph)
        }
    }
}