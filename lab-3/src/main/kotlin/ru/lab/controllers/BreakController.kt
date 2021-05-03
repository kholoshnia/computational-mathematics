package ru.lab.controllers

import ru.lab.model.Function
import tornadofx.Controller


class BreakController : Controller() {
    fun checkElseNext(
        function: Function,
        current: Double,
        right: Double,
        accuracy: Double
    ): Double {
        var x = current

        while (x <= right) {
            try {
                function(x)
                break
            } catch (_: ArithmeticException) {
                x += accuracy
            }
        }

        if (x > right + accuracy) {
            throw IllegalArgumentException("Could not find next value")
        }

        return x
    }

    fun checkElsePrev(
        function: Function,
        current: Double,
        left: Double,
        accuracy: Double
    ): Double {
        var x = current

        while (x >= left) {
            try {
                function(x)
                break
            } catch (_: ArithmeticException) {
                x -= accuracy
            }
        }

        if (x < left - accuracy) {
            throw IllegalArgumentException("Could not find previous value")
        }

        return x
    }
}