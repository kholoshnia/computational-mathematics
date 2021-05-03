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
    companion object {
        private const val MULTIPLY_NUMBER = 2
        private const val MAX_ITERATIONS_NUMBER = 1000000
    }

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
        partitioning: Int,
        accuracy: Double
    ): Double {
        return when (method) {
            Method.SIMPSONS -> {
                simpsonsMethod.compute(
                    function,
                    leftBoundary,
                    rightBoundary,
                    partitioning,
                    accuracy
                )
            }
            Method.TRAPEZOIDS -> {
                trapezoidsMethod.compute(
                    function,
                    leftBoundary,
                    rightBoundary,
                    partitioning,
                    accuracy
                )
            }
            else -> when (rectangle) {
                Rectangle.RIGHT -> {
                    rectangleMethod.right(
                        function,
                        leftBoundary,
                        rightBoundary,
                        partitioning,
                        accuracy
                    )
                }
                Rectangle.MIDDLE -> {
                    rectangleMethod.middle(
                        function,
                        leftBoundary,
                        rightBoundary,
                        partitioning,
                        accuracy
                    )
                }
                else -> {
                    rectangleMethod.left(
                        function,
                        leftBoundary,
                        rightBoundary,
                        partitioning,
                        accuracy
                    )
                }
            }
        }
    }

    fun showResults() {
        val function = formController.getFunction()
        val leftBoundary = formController.getLeftBoundary()
        val rightBoundary = formController.getRightBoundary()
        val partitioning = formController.getPartitioning()
        val method = formController.getMethod()
        val rectangle = formController.getRectangle()
        val useAccuracy = formController.useAccuracy()
        val accuracy = formController.getAccuracy()

        val left = breakController.checkElseNext(function, leftBoundary, rightBoundary, accuracy)
        val right = breakController.checkElsePrev(function, rightBoundary, leftBoundary, accuracy)

        var result = compute(
            method,
            rectangle,
            function,
            left,
            right,
            partitioning,
            accuracy
        )

        var nextResult = result
        var nextPartitioning = partitioning

        if (useAccuracy) {
            do {
                nextPartitioning *= MULTIPLY_NUMBER
                result = nextResult
                nextResult = compute(
                    method,
                    rectangle,
                    function,
                    left,
                    right,
                    nextPartitioning,
                    accuracy
                )
            } while (abs(nextResult - result) > accuracy
                && nextPartitioning < MAX_ITERATIONS_NUMBER
            )
        }

        resultsView.valueValue.text = nextResult.toString()
        resultsView.partitioningValue.text = nextPartitioning.toString()
    }
}