package ru.lab.slae.calculator

/**
 * SLAE calculator interface provides methods for working
 * with a system of linear algebraic equations.
 */
interface SlaeCalculator {
    /**
     * Calculates determinant of the given SLAE.
     * @return determinant for the given SLAE
     */
    fun determinant(): Double

    /**
     * Creates triangular matrix for the given SLAE.
     * @return triangular matrix for the give SLAE
     */
    fun triangular(): Array<DoubleArray>

    /**
     * Finds roots for the given SLAE using the Gaussian method.
     * @return array of roots for the given SLAE
     */
    fun roots(): DoubleArray

    /**
     * Funds residuals for the given SLAE.
     * @return array of residuals for the given SLAE
     */
    fun residuals(): DoubleArray
}