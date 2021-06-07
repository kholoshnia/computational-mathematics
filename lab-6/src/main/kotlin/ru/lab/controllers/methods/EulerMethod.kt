package ru.lab.controllers.methods

import ru.lab.model.Function
import ru.lab.model.Ode
import ru.lab.model.Results
import ru.lab.views.ResultsView
import tornadofx.Controller
import kotlin.math.abs


class EulerMethod : Controller() {
    private val resultsView: ResultsView by inject()

    private fun computeAccuracy(
        solution: Function,
        xValues: List<Double>,
        yValues: List<Double>,
    ): Double {
        var max = abs(solution(xValues[0]) - yValues[0])

        for (i in xValues.indices) {
            val newMax = abs(solution(xValues[i]) - yValues[i])
            if (newMax > max) {
                max = newMax
            }
        }

        return max
    }

    fun getFunction(
        ode: Ode,
        initialY: Double,
        step: Double,
        left: Double,
        right: Double,
        solution: Function,
        accuracy: Double,
        useAccuracy: Boolean
    ): Pair<List<Double>, List<Double>> {
        resultsView.rows.clear()

        val xValues = ArrayList<Double>()
        val yValues = ArrayList<Double>()

        var h = step
        var i: Int
        var x: Double
        var y: Double

        do {
            xValues.clear()
            yValues.clear()
            resultsView.rows.clear()
            i = 0

            x = left
            y = initialY

            xValues.add(x)
            yValues.add(y)

            while (x < right) {
                val odeValue = ode(x, y)
                resultsView.rows.add(Results(i, x, y, odeValue))
                i++

                y += h * odeValue
                x += h

                xValues.add(x)
                yValues.add(y)
            }

            h /= 2
        } while (accuracy < computeAccuracy(solution, xValues, yValues) && useAccuracy)

        resultsView.rows.add(Results(i, x, y, ode(x, y)))
        return Pair(xValues, yValues)
    }
}