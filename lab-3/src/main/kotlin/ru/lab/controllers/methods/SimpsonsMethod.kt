package ru.lab.controllers.methods

import ru.lab.controllers.BreakController
import ru.lab.model.Function
import tornadofx.Controller


class SimpsonsMethod : Controller() {
    private val breakController: BreakController by inject()

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
            val nextX = breakController.checkElseNext(function, x, right, accuracy)
            result += function(nextX)

            while (x <= nextX) {
                x += h * 2
            }
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

        val oddValue = sum(h, left + h, right - h, function, accuracy)
        val evenValue = sum(h, left + h * 2, right - h * 2, function, accuracy)

        return h / 3.0 * (function(left) + 4.0 * oddValue + 2.0 * evenValue + function(right))
    }
}
