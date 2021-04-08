package ru.lab.views

import javafx.beans.property.Property
import javafx.beans.property.SimpleListProperty
import javafx.collections.ObservableList
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.View
import tornadofx.linechart
import tornadofx.series
import tornadofx.toObservable
import tornadofx.vbox
import kotlin.math.pow

class GraphView : View() {
    private val graph: Property<ObservableList<XYChart.Data<Number, Number>>> = SimpleListProperty()

    private fun y(x: Double): Double {
        return 1.62 * x.pow(3) - 8.15 * x.pow(2) + 4.39 * x + 3.29
    }

    init {
        val functionString = "1,62x^3-8,15x^2+3,39x+4,29"
        var function = functionString.replace(",", ".")

        val left = -20.0
        val right = 20.0
        val step = 0.1

        val graphValues = HashMap<Number, Number>()

        var x = left
        while (x <= right) {
            graphValues[x] = y(x)
            x += step
        }

        graph.value = graphValues.map { (x, y) ->
            XYChart.Data(x, y)
        }.toObservable()
    }

    override val root = vbox {
        linechart("Function graph", NumberAxis(), NumberAxis()) {
            series("Function graph", elements = graph.value)
        }
    }
}