package ru.lab.controllers.methods

import ru.lab.model.Function
import tornadofx.Controller

class SimpsonsMethod : Controller() {
    private fun sum(
        h: Double,
        left: Double,
        right: Double,
        function: Function,
    ): Double {
        var x = left
        var result = 0.0
        while (x <= right) {
            result += function(x)
            x += h * 2
        }
        return result
    }

    fun compute(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int,
    ): Double {
        val h = (right - left) / partitioning

        val oddValue = sum(h, left + h, right, function)
        val evenValue = sum(h, left + h * 2, right - h, function)

        return h / 3.0 * (function(left) + 4.0 * oddValue + 2.0 * evenValue + function(right))
    }
}
