package ru.lab.controllers

import ru.lab.model.Function
import ru.lab.model.Half
import ru.lab.views.HalfView
import tornadofx.Controller
import kotlin.math.abs

class ComputeController : Controller() {
    private val halfView: HalfView by inject()

    fun check(
        function: Function,
        left: Double,
        right: Double,
    ): Boolean {
        val rise = (function(left + 0.001) - function(left)) / 0.001

        var x = left
        while (x < right) {
            val derivative = (function(x + 0.001) - function(x)) / 0.001
            x += 0.001

            if (derivative * rise <= 0) {
                return false
            }
        }

        return function(left) * function(right) < 0
    }

    fun derivative(function: Function, x: Double): Double {
        return (function(x + 0.001) - function(x)) / 0.001
    }

    fun halfDivision(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ) {
        halfView.rows.clear()

        var a = left
        var b = right
        var x = (a + b) / 2.0

        var i = 0

        halfView.rows.add(Half(i, a, b, x, function(a), function(b), function(x), abs(a - b)))

        while (abs(function(x)) > accuracy && abs(a - b) > accuracy) {
            x = (a + b) / 2.0
            val fa = function(a)
            val fb = function(b)
            val fx = function(x)

            if (fa * fx > 0) {
                a = x
            } else {
                b = x
            }

            i++
            halfView.rows.add(Half(i, a, b, x, fa, fb, fx, abs(a - b)))
        }
    }

    fun newton(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ) {
        var x0 = left
        var x = left
        while (x < right) {
            x0 = function(x) * derivative(function, derivative(function, x))
            if (x0 > 0) {
                break
            }
            x += accuracy
        }

        var xPrev = x0
        var xNext: Double

        while (/*abs(x - xPrev) > accuracy && abs(function(x)/ derivative(function, x)) > accuracy && */abs(function(xPrev)) > accuracy) {
            xNext = xPrev - function(xPrev) / derivative(function, xPrev)
            println("X next: $xNext x prev: $xPrev")
            xPrev = xNext
        }
    }

    fun iter(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ) {

    }
}