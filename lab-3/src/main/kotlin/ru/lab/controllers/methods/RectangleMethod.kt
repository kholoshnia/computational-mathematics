package ru.lab.controllers.methods

import ru.lab.model.Function
import ru.lab.model.Rectangle
import tornadofx.Controller

class RectangleMethod : Controller() {
    fun compute(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int,
        rectangle: Rectangle
    ): Double {
        val h = (right - left) / partitioning
        var value = 0.0

        val typeFunction = when (rectangle) {
            Rectangle.MIDDLE -> {
                fun(x: Double) = function(x - h / 2.0)
            }
            Rectangle.RIGHT -> {
                fun(x: Double) = function(x)
            }
            else -> {
                fun(x: Double) = function(x - h)
            }
        }

        var x = left + h
        while (x <= right) {
            try {
                value += typeFunction(x)
            } catch (_: ArithmeticException) {
            }

            x += h
        }

        return h * value
    }
}