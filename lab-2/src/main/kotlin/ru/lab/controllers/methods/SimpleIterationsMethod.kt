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

    private fun maxDf(a: Double, b: Double, function: Function, accuracy: Double): Double {
        var x = a
        var derivative = function.derivative(a, accuracy)
        var max = derivative
        while (x < b) {
            x += accuracy
            derivative = function.derivative(x, accuracy)
            if (derivative > max) {
                max = derivative
            }
        }
        return max
    }

    private fun maxDfi(a: Double, b: Double, lambda: Double, function: Function, accuracy: Double): Double {
        var x = a
        var derivative = dfi(x, lambda, function, accuracy)
        var max = derivative
        while (x < b) {
            x += accuracy
            derivative = dfi(x, lambda, function, accuracy)
            if (derivative > max) {
                max = derivative
            }
        }
        return max
    }

    private fun fi(x: Double, lambda: Double, function: Function): Double {
        return x + lambda * function(x)
    }

    private fun dfi(lambda: Double, x: Double, function: Function, accuracy: Double): Double {
        return (fi(lambda, x + accuracy, function) - fi(lambda, x, function)) / accuracy
    }

    private fun stopCriteria(xNext: Double, xPrev: Double, accuracy: Double, q: Double): Boolean {
        return if (q > 0 && q <= 0.5) {
            abs(xNext - xPrev) <= accuracy
        } else if (q > 0.5 && q < 1) {
            abs(xNext - xPrev) <= (1 - q) / q * accuracy
        } else {
            false
        }
    }

    fun compute(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ) {
        simpleIterationsView.rows.clear()
        val lambda = 1 / maxDf(left, right, function, accuracy)

        var n = 0
        var xPrev = methodController.findX0(function, left, right, accuracy)
        var xNext = fi(xPrev, lambda, function)
        var fx = function(xPrev)
        var dfi = dfi(xPrev, lambda, function, accuracy)
        var q = maxDfi(left, right, lambda, function, accuracy) * dfi
        var xPrevXNext = abs(xPrev - xNext)

        println("q=$q, dfi=$dfi, fi=$dfi")

        while (!stopCriteria(xPrev, xNext, accuracy, q)) {
            simpleIterationsView.rows.add(SimpleIterationsResults(n, xPrev, fx, xNext, xNext, xPrevXNext))

            xNext = fi(xPrev, lambda, function)
            xPrev = xNext

            fx = function(xPrev)
            dfi = dfi(xPrev, lambda, function, accuracy)
            q = maxDfi(left, right, lambda, function, accuracy) * dfi
            xPrevXNext = abs(xPrev - xNext)
            n++
        }

        simpleIterationsView.rows.add(SimpleIterationsResults(n, xPrev, fx, xNext, xNext, xPrevXNext))
        methodController.lastResults = Results(xPrev, fx, n)
    }
}
