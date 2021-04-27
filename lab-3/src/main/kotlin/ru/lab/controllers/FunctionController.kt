package ru.lab.controllers

import javafx.collections.ObservableList
import javafx.scene.chart.XYChart
import net.objecthunter.exp4j.ExpressionBuilder
import ru.lab.model.Function
import tornadofx.Controller

class FunctionController : Controller() {
    fun getFunction(functionString: String): Function {
        val function = functionString.replace(",", ".")
        val expression = ExpressionBuilder(function)
            .variable(Function.VARIABLE_NAME)
            .build()
        return Function(expression)
    }

    fun updateValues(
        seriesList: ObservableList<XYChart.Series<Number, Number>>,
        function: Function,
        left: Double,
        right: Double,
        step: Double
    ) {
        var x = left
        var gaps = 0
        seriesList.clear()

        while (x <= right) {
            try {
                val value = function(x)
                seriesList.getOrElse(gaps) {
                    try {
                        seriesList.add(gaps, XYChart.Series())
                    } catch (e: IndexOutOfBoundsException) {
                        gaps = 0
                        seriesList.add(gaps, XYChart.Series())
                    }

                    seriesList[gaps]
                }.data.add(XYChart.Data(x, value))
            } catch (e: ArithmeticException) {
                gaps++
            }

            x += step
        }
    }
}