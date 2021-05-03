package ru.lab.controllers.methods

import ru.lab.controllers.BreakController
import ru.lab.model.Function
import tornadofx.Controller


class RectangleMethod : Controller() {
    private val breakController: BreakController by inject()

    fun left(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int,
        accuracy: Double,
    ): Double {
        val h = (right - left) / partitioning
        var result = 0.0
        var x = left + h
        var prev = left

        while (x <= right) {
            val current = x - h
            var nextX = breakController.checkElsePrev(function, current, prev, accuracy)

            result += function(nextX)

            prev = current
            while (nextX <= current) {
                x += h
                nextX += h
            }
        }

        return h * result
    }

    fun right(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int,
        accuracy: Double,
    ): Double {
        val h = (right - left) / partitioning
        var result = 0.0
        var x = left + h

        while (x <= right) {
            val nextX = breakController.checkElseNext(function, x, right, accuracy)

            result += function(nextX)

            while (x <= nextX) {
                x += h
            }
        }

        return h * result
    }

    fun middle(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int,
        accuracy: Double,
    ): Double {
        val h = (right - left) / partitioning
        var result = 0.0
        var x = left + h

        while (x <= right) {
            var current = x - h / 2.0
            val nextX = breakController.checkElseNext(function, current, right, accuracy)

            result += function(nextX)

            while (current <= nextX) {
                x += h
                current = x - h / 2.0
            }
        }

        return h * result
    }
}