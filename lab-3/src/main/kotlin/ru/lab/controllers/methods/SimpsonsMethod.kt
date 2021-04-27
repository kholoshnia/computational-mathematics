package ru.lab.controllers.methods

import ru.lab.model.Function
import tornadofx.Controller

class SimpsonsMethod : Controller() {
    private fun sum(
        h: Double,
        left: Double,
        right: Double,
        function: Function,
        accuracy: Double
    ): Double {
        var x = left
        var result = 0.0
        while (x <= right) {
            result += try {
                function(x)
            } catch (_: ArithmeticException) {
                function(x + accuracy)
            }

            x += h * 2
        }
        return result
    }

    fun compute(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int,
        accuracy: Double
    ): Double {
        val h = (right - left) / partitioning

        val oddValue = sum(h, left + h, right, function, accuracy)
        val evenValue = sum(h, left + h * 2, right - h, function, accuracy)

        val leftValue = try {
            function(left)
        } catch (_: ArithmeticException) {
            function(left + accuracy)
        }

        return h / 3.0 * (leftValue + 4.0 * oddValue + 2.0 * evenValue + function(right))
    }
}
