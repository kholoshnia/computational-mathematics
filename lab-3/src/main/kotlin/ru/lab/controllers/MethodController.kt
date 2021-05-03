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
        function: Function,
        leftBoundary: Double,
        rightBoundary: Double,
        partitioning: Int,
        accuracy: Double,
        rectangle: Rectangle
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
            function,
            left,
            right,
            partitioning,
            accuracy,
            rectangle
        )

        var nextResult = result
        var nextPartitioning = partitioning

        if (useAccuracy) {
            do {
                nextPartitioning *= 2
                result = nextResult
                nextResult = compute(
                    method,
                    function,
                    left,
                    right,
                    nextPartitioning,
                    accuracy,
                    rectangle
                )
            } while (abs(nextResult - result) > accuracy)
        }

        resultsView.valueValue.text = nextResult.toString()
        resultsView.partitioningValue.text = nextPartitioning.toString()
    }
}