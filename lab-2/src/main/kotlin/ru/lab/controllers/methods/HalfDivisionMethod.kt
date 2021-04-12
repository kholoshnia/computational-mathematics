package ru.lab.controllers.methods

import ru.lab.controllers.MethodController
import ru.lab.model.Function
import ru.lab.model.Results
import ru.lab.model.results.HalfDivisionResults
import ru.lab.views.tables.HalfDivisionView
import tornadofx.Controller
import kotlin.math.abs

class HalfDivisionMethod : Controller() {
    private val methodController: MethodController by inject()
    private val halfDivisionView: HalfDivisionView by inject()

    private fun stopCriteria(
        function: Function,
        x: Double,
        accuracy: Double,
        a: Double,
        b: Double
    ) = abs(function(x)) > accuracy && abs(a - b) > accuracy

    fun compute(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ) {
        halfDivisionView.rows.clear()

        var n = 1
        var a = left
        var b = right
        var x = (a + b) / 2.0
        var fa = function(a)
        var fb = function(b)
        var fx = function(x)
        var ab = abs(a - b)

        while (stopCriteria(function, x, accuracy, a, b)) {
            halfDivisionView.rows.add(HalfDivisionResults(n, a, b, x, fa, fb, fx, ab))

            x = (a + b) / 2.0
            fa = function(a)
            fb = function(b)
            fx = function(x)
            ab = abs(a - b)

            if (fa * fx > 0) {
                a = x
            } else {
                b = x
            }

            n++
        }

        halfDivisionView.rows.add(HalfDivisionResults(n, a, b, x, fa, fb, fx, ab))
        methodController.lastResults = Results(x, fx, n)
    }
}