package ru.lab.controllers

import ru.lab.model.Function
import tornadofx.Controller
import kotlin.math.roundToInt


class BreakController : Controller() {
    fun getIntervals(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ): List<Pair<Double, Double>> {
        val intervals = ArrayList<Pair<Double, Double>>()
        var lastLeft = left

        var x = left
        while (x <= right) {
            try {
                function((x / accuracy).roundToInt() * accuracy)
            } catch (_: ArithmeticException) {
                lastLeft = if (x == left) {
                    x + accuracy
                } else {
                    intervals.add(Pair(lastLeft, x - accuracy))
                    x + accuracy
                }
            }

            x += accuracy
        }

        if (lastLeft < right) {
            intervals.add(Pair(lastLeft, right))
        }

        return intervals
    }
}