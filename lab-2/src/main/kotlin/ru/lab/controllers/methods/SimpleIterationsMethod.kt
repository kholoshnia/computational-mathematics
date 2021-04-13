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
        val da = function.derivative(a, accuracy)
        val db = function.derivative(b, accuracy)
        return if (da > db) da else db
    }

    private fun dfi(
        x: Double,
        lambda: Double,
        function: Function,
        accuracy: Double
    ) = 1 + lambda * function.derivative(x, accuracy)

    private fun fi(
        x: Double,
        lambda: Double,
        function: Function
    ) = x + lambda * function(x)

    private fun stopCriteria(
        xPrevXNext: Double,
        accuracy: Double
    ) = xPrevXNext <= accuracy

    fun compute(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ) {
        simpleIterationsView.rows.clear()

        val lambda = -1.0 / maxDf(left, right, function, accuracy)
        println("Lambda=$lambda")

        var n = 0
        var xPrev = methodController.findX0(function, left, right, accuracy)
        var xNext = fi(xPrev, lambda, function)
        var fx = function(xPrev)
        var xPrevXNext = abs(xPrev - xNext)

        if (dfi(left, lambda, function, accuracy) >= 1
            && dfi(right, lambda, function, accuracy) <= 1
        ) {
            throw IllegalArgumentException("Process is not continuous.")
        }

        while (!stopCriteria(xPrevXNext, accuracy)) {
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
