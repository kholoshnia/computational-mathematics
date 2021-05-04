package ru.lab.controllers.methods

import ru.lab.model.Function
import tornadofx.Controller


class RectangleMethod : Controller() {
    fun left(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int
    ): Double {
        val h = (right - left) / partitioning
        var result = 0.0
        var x = left + h

        while (x <= right) {
            result += function(x)
            x += h
        }

        return h * result
    }

    fun right(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int
    ): Double {
        val h = (right - left) / partitioning
        var result = 0.0
        var x = left

        while (x <= right - h) {
            result += function(x)
            x += h
        }

        return h * result
    }

    fun middle(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int
    ): Double {
        val h = (right - left) / partitioning
        var result = 0.0
        var x = left + h

        while (x <= right) {
            result += function(x - h / 2.0)
            x += h
        }

        return h * result
    }
}