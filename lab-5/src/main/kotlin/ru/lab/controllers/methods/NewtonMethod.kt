package ru.lab.controllers.methods

import tornadofx.Controller


class NewtonMethod : Controller() {
    private fun firstOrder(
        i1: Int,
        i2: Int,
        xValues: List<Double>,
        yValues: List<Double>
    ): Double {
        return (yValues[i2] - yValues[i1]) / (xValues[i2] - xValues[i1])
    }

    private fun dividedDifferences(
        from: Int,
        to: Int,
        xValues: List<Double>,
        yValues: List<Double>,
        cacheMap: MutableMap<Pair<Int, Int>, Double>
    ): Double {
        if (to - from == 1) {
            return firstOrder(from, to, xValues, yValues)
        }

        if (cacheMap.containsKey(Pair(from, to))) {
            return cacheMap[Pair(from, to)]!!
        }

        val dividedDifference = dividedDifferences(from + 1, to, xValues, yValues, cacheMap)
        cacheMap[Pair(from + 1, to)] = dividedDifference

        val firstOrder = firstOrder(from, to - 1, xValues, yValues)
        val denominator = xValues[to] - xValues[from]

        return (dividedDifference - firstOrder) / denominator
    }

    fun getFunction(
        xValues: List<Double>,
        yValues: List<Double>
    ): String {
        val f0 = yValues[0]

        val sum = mutableListOf<String>()
        val product = mutableListOf<String>()
        val cacheMap = mutableMapOf<Pair<Int, Int>, Double>()

        for (i in 0 until xValues.size - 1) {
            product.clear()
            for (j in 0..i) {
                product.add("(x-${xValues[j]})")
            }

            sum.add(
                "(${dividedDifferences(0, i + 1, xValues, yValues, cacheMap)}*${
                    product.joinToString("*")
                })"
            )
        }

        return "$f0+${sum.joinToString("+")}"
    }
}