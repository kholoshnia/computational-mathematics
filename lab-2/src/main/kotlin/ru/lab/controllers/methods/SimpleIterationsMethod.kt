package ru.lab.controllers.methods

import ru.lab.controllers.MethodController
import ru.lab.model.Function
import ru.lab.model.Results
import ru.lab.model.results.SimpleIterationsResults
import ru.lab.views.tables.SimpleIterationsView
import tornadofx.Controller
import kotlin.math.abs

class SimpleIterationsMethod : Controller() {
    private val methodController: MethodController by inject()
    private val simpleIterationsView: SimpleIterationsView by inject()

    private fun maxDf(
        a: Double,
        b: Double,
        function: Function,
        accuracy: Double
    ): Double {
        var x = a
        var derivative = function.derivative(x, accuracy)
        var max = derivative
        while (x <= b) {
            x += accuracy
            derivative = function.derivative(x, accuracy)
            if (derivative > max) {
                max = derivative
            }
        }
        return max
    }

    private fun fi(
        x: Double,
        lambda: Double,
        function: Function
    ) = x + lambda * function(x)

    private fun stopCriteria(
        xNext: Double,
        xPrev:
        Double,
        accuracy: Double
    ) = abs(xNext - xPrev) <= accuracy

    fun compute(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ) {
        simpleIterationsView.rows.clear()

        val lambda = -1.0 / maxDf(left, right, function, accuracy)

        var n = 0
        var xPrev = methodController.findX0(function, left, right, accuracy)
        var xNext = fi(xPrev, lambda, function)
        var fx = function(xPrev)
        var xPrevXNext = abs(xPrev - xNext)

        while (!stopCriteria(xPrev, xNext, accuracy)) {
            simpleIterationsView.rows.add(SimpleIterationsResults(n, xPrev, fx, xNext, xNext, xPrevXNext))

            xPrev = xNext
            xNext = fi(xPrev, lambda, function)

            fx = function(xPrev)
            xPrevXNext = abs(xPrev - xNext)
            n++
        }

        simpleIterationsView.rows.add(SimpleIterationsResults(n, xPrev, fx, xNext, xNext, xPrevXNext))
        methodController.lastResults = Results(xPrev, fx, n)
    }
}
