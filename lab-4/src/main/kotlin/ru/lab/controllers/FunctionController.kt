package ru.lab.controllers

import javafx.scene.chart.XYChart
import net.objecthunter.exp4j.ExpressionBuilder
import ru.lab.model.Function
import tornadofx.Controller


class FunctionController : Controller() {
    companion object {
        private const val MIN_Y = -1000
        private const val MAX_Y = 1000
    }

    fun getFunction(functionString: String): Function {
        val function = functionString.replace(",", ".")
        val expression = ExpressionBuilder(function)
            .variable(Function.VARIABLE_NAME)
            .build()
        return Function(expression)
    }

    fun getSeries(
        name: String,
        function: Function,
        left: Double,
        right: Double,
        step: Double
    ): XYChart.Series<Number, Number> {
        val series = XYChart.Series<Number, Number>()
        series.name = name

        var x = left
        while (x <= right) {
            try {
                val value = function(x)
                if (value > MIN_Y && value < MAX_Y) {
                    series.data.add(XYChart.Data(x, value))
                }
            } catch (_: ArithmeticException) {
            }

            x += step
        }

        return series
    }

    fun getSeries(
        name: String,
        xValues: List<Double>,
        yValues: List<Double>,
    ): XYChart.Series<Number, Number> {
        val series = XYChart.Series<Number, Number>()
        series.name = name

        for (i in xValues.indices) {
            series.data.add(XYChart.Data(xValues[i], yValues[i]))
        }

        return series
    }
}