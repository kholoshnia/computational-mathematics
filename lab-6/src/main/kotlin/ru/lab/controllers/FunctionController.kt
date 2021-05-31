package ru.lab.controllers

import javafx.scene.chart.XYChart
import net.objecthunter.exp4j.ExpressionBuilder
import ru.lab.model.Function
import ru.lab.model.Ode
import tornadofx.Controller


class FunctionController : Controller() {
    fun getFunction(functionString: String): Function {
        val function = functionString.replace(",", ".")
        val expression = ExpressionBuilder(function)
            .variable(Function.VARIABLE_NAME)
            .build()
        return Function(expression)
    }

    fun getOde(odeString: String): Ode {
        val function = odeString.replace(",", ".")
        val expression = ExpressionBuilder(function)
            .variables(Ode.X_VARIABLE_NAME, Ode.Y_VARIABLE_NAME)
            .build()
        return Ode(expression)
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
                series.data.add(XYChart.Data(x, value))
            } catch (_: ArithmeticException) {
            }

            x += step
        }

        return series
    }

    fun getSeries(
        name: String,
        xValues: List<Double>,
        yValues: List<Double>
    ): XYChart.Series<Number, Number> {
        val series = XYChart.Series<Number, Number>()
        series.name = name

        for (i in xValues.indices) {
            if (xValues.size > 1000) {
                if (i % (xValues.size / 100) == 0) {
                    series.data.add(XYChart.Data(xValues[i], yValues[i]))
                }
            } else {
                series.data.add(XYChart.Data(xValues[i], yValues[i]))
            }
        }

        return series
    }
}