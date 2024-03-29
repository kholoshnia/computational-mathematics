package ru.lab.controllers.methods

import tornadofx.Controller


class LagrangeMethod : Controller() {
    fun getFunction(
        xValues: List<Double>,
        yValues: List<Double>
    ): String {
        val stringList = ArrayList<String>()

        xValues.forEachIndexed { i, x ->
            val numerator = xValues
                .filter { it != x }
                .joinToString("*") { "(x-$it)" }

            val denominator = xValues
                .filter { it != x }
                .map { x - it }
                .reduce { acc, current -> acc * current }

            stringList.add("($numerator)/$denominator*${yValues[i]}")
        }

        return stringList.joinToString("+")
    }
}