package ru.lab.calculator

/**
 * SLAE calculator implementation.
 * @see SlaeCalculator
 */
class SlaeCalculatorImpl(val matrix: List<List<Double>>) : SlaeCalculator() {
    override fun determinant(triangular: List<List<Double>>): Double {
        return 0.0
    }

    override fun triangular(): List<List<Double>> {
        return listOf()
    }

    override fun roots(): List<Double> {
        return listOf()
    }

    override fun residuals(): List<Double> {
        return listOf()
    }

}
