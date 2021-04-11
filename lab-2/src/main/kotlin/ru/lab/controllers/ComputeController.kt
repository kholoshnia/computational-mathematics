package ru.lab.controllers

import ru.lab.model.Function
import ru.lab.model.Half
import ru.lab.views.HalfView
import tornadofx.Controller
import kotlin.math.abs

class ComputeController : Controller() {
    private val halfView: HalfView by inject()

    fun halfDivision(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ) {
        var a = left
        var b = right
        var x = (a + b) / 2

        var i = 0
        while (abs(a - b) > accuracy || abs(function(x)) > accuracy) {
            i++
            x = (a + b) / 2
            val fa = function(a)
            val fb = function(b)
            val fx = function(x)

            if (fa * fx > 0) {
                a = x
            } else {
                b = x
            }

            halfView.rows.add(Half(i, a, b, x, fa, fb, fx, abs(a - b)))
        }
    }
}