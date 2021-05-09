package ru.lab.controllers.approximations

import ru.lab.controllers.Approximation
import ru.lab.controllers.SlaeController
import tornadofx.Controller
import kotlin.math.E
import kotlin.math.ln
import kotlin.math.pow


class ExponentialApproximation : Approximation, Controller() {
    private val slaeController: SlaeController by inject()

    override fun getFunction(
        xValues: List<Double>,
        yValues: List<Double>
    ): String? {
        if (yValues.any { it <= 0 }) {
            return null
        }

        val n = xValues.size.toDouble()
        val sx = xValues.sumOf { it }
        val sx2 = xValues.sumOf { it.pow(2) }
        val sy = yValues.sumOf { ln(it) }

        var sxy = 0.0
        for (i in xValues.indices) {
            sxy += xValues[i] * ln(yValues[i])
        }

        val (a, b) = slaeController.solve(
            arrayOf(
                doubleArrayOf(sx2, sx, sxy),
                doubleArrayOf(sx, n, sy)
            )
        )

        return "$E^$a*$E^(${b}x)"
    }
}