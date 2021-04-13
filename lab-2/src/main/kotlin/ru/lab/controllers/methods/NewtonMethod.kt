package ru.lab.controllers.methods

import ru.lab.controllers.MethodController
import ru.lab.model.Function
import ru.lab.model.Results
import ru.lab.model.results.NewtonResults
import ru.lab.views.tables.NewtonView
import tornadofx.Controller
import kotlin.math.abs

class NewtonMethod : Controller() {
    private val methodController: MethodController by inject()
    private val newtonView: NewtonView by inject()

    private fun stopCriteria(
        xPrev: Double,
        function: Function,
        accuracy: Double
    ) = abs(function(xPrev) / function.derivative(xPrev, accuracy)) < accuracy
            || abs(function(xPrev)) < accuracy

    fun compute(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ) {
        newtonView.rows.clear()

        var n = 1
        var xPrev = methodController.findX0(function, left, right, accuracy)
        var xNext = xPrev - function(xPrev) / function.derivative(xPrev, accuracy)
        var fx = function(xPrev)
        var dx = function.derivative(xPrev, accuracy)
        var xPrevXNext = abs(xPrev - xNext)

        while (!stopCriteria(xPrev, function, accuracy)) {
            newtonView.rows.add(NewtonResults(n, xPrev, fx, dx, xNext, xPrevXNext))

            xPrev = xNext
            xNext = xPrev - function(xPrev) / function.derivative(xPrev, accuracy)

            fx = function(xPrev)
            dx = function.derivative(xPrev, accuracy)
            xPrevXNext = abs(xPrev - xNext)
            n++
        }

        newtonView.rows.add(NewtonResults(n, xPrev, fx, dx, xNext, xPrevXNext))
        methodController.lastResults = Results(xPrev, fx, n)
    }
}
