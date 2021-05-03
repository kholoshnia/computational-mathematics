package ru.lab.controllers.methods

import ru.lab.controllers.BreakController
import ru.lab.model.Function
import tornadofx.Controller


class TrapezoidsMethod : Controller() {
    private val breakController: BreakController by inject()

    fun compute(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int,
        accuracy: Double
    ): Double {
        val h = (right - left) / partitioning
        var result = 0.0
        var x = left + h

        while (x <= right - h) {
            val nextX = breakController.checkElseNext(function, x, right, accuracy)
            result += function(nextX)

            while (x <= nextX) {
                x += h
            }
        }

        return h * ((function(left) + function(right)) / 2.0 + result)
    }
}
