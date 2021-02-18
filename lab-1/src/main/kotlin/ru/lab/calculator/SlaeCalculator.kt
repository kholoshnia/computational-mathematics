package ru.lab.calculator

/**
 * SLAE calculator interface provides methods for working
 * with a system of linear algebraic equations.
 */
abstract class SlaeCalculator {
    fun calculate(): Result {
        val triangular = triangular()
        return Result(
            determinant(triangular),
            triangular,
            roots(),
            residuals()
        )
    }

    /**
     * Calculates determinant of the given SLAE.
     * @param triangular triangular matrix
     * @return determinant for the given SLAE
     */
    abstract fun determinant(triangular: List<List<Double>>): Double

    /**
     * Creates triangular matrix for the given SLAE.
     * @return triangular matrix for the give SLAE
     */
    abstract fun triangular(): List<List<Double>>

    /**
     * Finds roots for the given SLAE using the Gaussian method.
     * @return list of roots for the given SLAE
     */
    abstract fun roots(): List<Double>

    /**
     * Finds residuals for the given SLAE.
     * @return list of residuals for the given SLAE
     */
    abstract fun residuals(): List<Double>
}