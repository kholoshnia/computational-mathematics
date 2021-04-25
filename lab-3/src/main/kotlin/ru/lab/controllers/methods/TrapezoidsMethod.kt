package ru.lab.controllers.methods

import ru.lab.model.Function
import tornadofx.Controller

class TrapezoidsMethod : Controller() {
    fun compute(
        function: Function,
        left: Double,
        right: Double,
        partitioning: Int,
    ): Double {
        val h = (right - left) / partitioning
        var value = 0.0

        var x = left + h
        while (x <= right) {
            value += function(x)
            x += h
        }

        return h * ((function(left) + function(right)) / 2.0 + value)
    }
}
