package ru.lab.controllers

import ru.lab.controllers.approximations.ExponentialApproximation
import ru.lab.controllers.approximations.LinearApproximation
import ru.lab.controllers.approximations.LogarithmApproximation
import ru.lab.controllers.approximations.PowerApproximation
import ru.lab.controllers.approximations.SquareApproximation
import ru.lab.model.Function
import ru.lab.model.Results
import ru.lab.model.Types
import ru.lab.views.GraphView
import ru.lab.views.ResultsView
import tornadofx.Controller
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


class ComputeController : Controller() {
    companion object {
        private const val LEFT_OFFSET = 10
        private const val RIGHT_OFFSET = 10
        private const val PIERSON = "Pierson coefficient: %s"
    }

    private val formController: FormController by inject()
    private val functionController: FunctionController by inject()
    private val graphView: GraphView by inject()
    private val resultsView: ResultsView by inject()

    private val linearApproximation: LinearApproximation by inject()
    private val squareApproximation: SquareApproximation by inject()
    private val exponentialApproximation: ExponentialApproximation by inject()
    private val logarithmApproximation: LogarithmApproximation by inject()
    private val powerApproximation: PowerApproximation by inject()

    private val approximationList = mapOf<Types, Approximation>(
        Types.LINEAR to linearApproximation,
        Types.SQUARE to squareApproximation,
        Types.EXPONENTIAL to exponentialApproximation,
        Types.LOGARITHM to logarithmApproximation,
        Types.POWER to powerApproximation,
    )

    private fun getDeviationMeasure(
        xValues: List<Double>,
        yValues: List<Double>,
        approximation: Function
    ): Double {
        var min = Double.MAX_VALUE
        for (i in xValues.indices) {
            val s = (approximation(xValues[i]) - yValues[i]).pow(2)
            if (s < min) {
                min = s
            }
        }
        return min
    }

    private fun getRmsDeviation(
        xValues: List<Double>,
        yValues: List<Double>,
        approximation: Function
    ): Double {
        var sum = 0.0
        for (i in xValues.indices) {
            sum += (approximation(xValues[i]) - yValues[i]).pow(2)
        }
        return sqrt(sum / xValues.size)
    }

    private fun getRSquare(
        xValues: List<Double>,
        yValues: List<Double>,
        approximation: Function
    ): Double {
        var numerator = 0.0
        for (i in xValues.indices) {
            numerator += (yValues[i] - approximation(xValues[i])).pow(2)
        }

        val denominator = xValues.sumOf { approximation(it).pow(2) } -
                1 / xValues.size * xValues.sumOf { approximation(it) }.pow(2)

        return 1 - numerator / denominator
    }

    private fun getPiersonCoefficient(
        xValues: List<Double>,
        yValues: List<Double>
    ): Double {
        val n = xValues.size
        val xAverage = xValues.sum() / n
        val yAverage = yValues.sum() / n

        var numerator = 0.0
        for (i in xValues.indices) {
            numerator += (xValues[i] - xAverage) * (yValues[i] - yAverage)
        }

        val denominator = sqrt(
            xValues.sumOf { (it - xAverage).pow(2) } *
                    yValues.sumOf { (it - yAverage).pow(2) }
        )

        return numerator / denominator
    }

    private fun getBest(resultsList: List<Results>): String {
        var best = ""
        var minRSquare = Double.MAX_VALUE

        resultsList.forEach {
            val distanceTo1 = abs(1 - it.rSquare.toDouble())
            if (distanceTo1 < minRSquare) {
                minRSquare = distanceTo1
                best = it.name
            }
        }

        return best
    }

    fun compute() {
        val xValues = formController.getXValues()
        val yValues = formController.getYValues()

        if (xValues.size != yValues.size) {
            throw IllegalArgumentException("X and Y values are not equal in size")
        }

        val step = formController.getStep()
        val left = formController.getLeftBoundary() ?: xValues.first() - LEFT_OFFSET
        val right = formController.getRightBoundary() ?: xValues.last() + RIGHT_OFFSET
        val resultsList = ArrayList<Results>()

        graphView.clear()
        resultsView.clear()

        approximationList.forEach { (type, it) ->
            val functionPair = it.getFunction(xValues, yValues) ?: return@forEach
            val name = type.printName
            val approximation = functionController.getFunction(functionPair.first)
            val deviationMeasure = getDeviationMeasure(xValues, yValues, approximation)
            val rmdDeviation = getRmsDeviation(xValues, yValues, approximation)
            val rSquare = getRSquare(xValues, yValues, approximation)

            val other = if (type == Types.LINEAR) {
                val pierson = String.format("%.3f", getPiersonCoefficient(xValues, yValues))
                PIERSON.format(pierson)
            } else ""

            val series = functionController.getSeries(name, approximation, left, right, step)
            graphView.addSeries(series)

            resultsList.add(
                Results(
                    name,
                    functionPair.first,
                    deviationMeasure.toString(),
                    rmdDeviation.toString(),
                    rSquare.toString(),
                    other
                )
            )

            resultsView.addResults(
                Results(
                    name,
                    functionPair.second,
                    String.format("%.3f", deviationMeasure),
                    String.format("%.3f", rmdDeviation),
                    String.format("%.3f", rSquare),
                    other
                )
            )
        }

        val source = functionController.getSeries("Source", xValues, yValues, left, right)
        graphView.addSeries(source)

        val best: String = getBest(resultsList)
        resultsView.setBest(best)
    }
}
