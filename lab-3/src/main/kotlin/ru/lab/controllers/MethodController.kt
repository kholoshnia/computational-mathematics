package ru.lab.controllers

import ru.lab.controllers.methods.RectangleMethod
import ru.lab.controllers.methods.SimpsonsMethod
import ru.lab.controllers.methods.TrapezoidsMethod
import ru.lab.model.Function
import ru.lab.model.Method
import ru.lab.model.Rectangle
import ru.lab.views.ResultsView
import tornadofx.Controller
import kotlin.math.abs


class MethodController : Controller() {
    private val formController: FormController by inject()
    private val breakController: BreakController by inject()
    private val resultsView: ResultsView by inject()

    private val rectangleMethod: RectangleMethod by inject()
    private val trapezoidsMethod: TrapezoidsMethod by inject()
    private val simpsonsMethod: SimpsonsMethod by inject()

    private fun compute(
        method: Method,
        rectangle: Rectangle,
        function: Function,
        leftBoundary: Double,
        rightBoundary: Double,
        partitioning: Int
    ): Double {
        return when (method) {
            Method.SIMPSONS -> simpsonsMethod.compute(
                function,
                leftBoundary,
                rightBoundary,
                partitioning
            )
            Method.TRAPEZOIDS -> trapezoidsMethod.compute(
                function,
                leftBoundary,
                rightBoundary,
                partitioning
            )
            else -> when (rectangle) {
                Rectangle.RIGHT -> rectangleMethod.right(
                    function,
                    leftBoundary,
                    rightBoundary,
                    partitioning
                )
                Rectangle.MIDDLE -> rectangleMethod.middle(
                    function,
                    leftBoundary,
                    rightBoundary,
                    partitioning
                )
                else -> rectangleMethod.left(
                    function,
                    leftBoundary,
                    rightBoundary,
                    partitioning
                )
            }
        }
    }

    private fun computeIntegral(
        method: Method,
        rectangle: Rectangle,
        function: Function,
        leftBoundary: Double,
        rightBoundary: Double,
        initialPartitioning: Int,
        useAccuracy: Boolean,
        accuracy: Double
    ): Pair<Double, Int> {
        val intervals = breakController.getIntervals(
            function,
            leftBoundary,
            rightBoundary,
            accuracy
        )

        var result = intervals.sumOf {
            compute(
                method,
                rectangle,
                function,
                it.first,
                it.second,
                initialPartitioning
            )
        }

        if (useAccuracy) {
            var nextResult = result
            var nextPartitioning = initialPartitioning

            do {
                nextPartitioning *= 2
                result = nextResult
                nextResult = intervals.sumOf {
                    compute(
                        method,
                        rectangle,
                        function,
                        it.first,
                        it.second,
                        nextPartitioning
                    )
                }
            } while (abs(nextResult - result) >= accuracy)

            return Pair(nextResult, nextPartitioning)
        }

        return Pair(result, initialPartitioning)
    }

    fun computeResults() {
        val function = formController.getFunction()
        val leftBoundary = formController.getLeftBoundary()
        val rightBoundary = formController.getRightBoundary()
        val initialPartitioning = formController.getInitialPartitioning()
        val method = formController.getMethod()
        val rectangle = formController.getRectangle()
        val useAccuracy = formController.useAccuracy()
        val accuracy = formController.getAccuracy()

        val (result, partitioning) = computeIntegral(
            method,
            rectangle,
            function,
            leftBoundary,
            rightBoundary,
            initialPartitioning,
            useAccuracy,
            accuracy
        )

        resultsView.valueValue.text = result.toString()
        resultsView.partitioningValue.text = partitioning.toString()
    }
}
