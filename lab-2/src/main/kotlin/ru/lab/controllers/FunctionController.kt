package ru.lab.controllers

import javafx.collections.ObservableList
import javafx.scene.chart.XYChart
import net.objecthunter.exp4j.ExpressionBuilder
import ru.lab.model.Function
import ru.lab.views.GraphView
import tornadofx.Controller

class FunctionController : Controller() {
    private val graphView: GraphView by inject()

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
        accuracy: Double
    ) {
        var x = left
        var gaps = 0
        seriesList.clear()

        while (x <= right) {
            val value = function(x)
            if (value.isNaN() || value.isInfinite()) {
                gaps++
            } else {
                seriesList.getOrElse(gaps) {
                    seriesList.add(gaps, XYChart.Series())
                    seriesList[gaps]
                }.data.add(XYChart.Data(x, value))
            }
            x += accuracy
        }
    }
}