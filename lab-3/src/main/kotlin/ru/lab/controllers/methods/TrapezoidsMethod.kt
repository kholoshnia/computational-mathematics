package ru.lab.controllers.methods

import ru.lab.model.Function
import tornadofx.Controller

class TrapezoidsMethod : Controller() {
    fun compute(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int,
        accuracy: Double
    ): Double {
        val h = (right - left) / partitioning
        var value = 0.0

        var x = left + h
        while (x <= right) {
            value += try {
                function(x)
            } catch (_: ArithmeticException) {
                function(x + accuracy)
            }

            x += h
        }

        val leftValue = try {
            function(left)
        } catch (_: ArithmeticException) {
            function(left + accuracy)
        }

        val rightValue = try {
            function(right)
        } catch (_: ArithmeticException) {
            function(right + accuracy)
        }

        return h * ((leftValue + rightValue) / 2.0 + value)
    }
}
