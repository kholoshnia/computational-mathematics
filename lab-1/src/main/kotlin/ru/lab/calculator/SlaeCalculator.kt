package ru.lab.calculator

/**
 * SLAE calculator interface provides methods for working
 * with a system of linear algebraic equations.
 */
interface SlaeCalculator {
    /**
     * Calculates determinant of the given SLAE.
     * @param triangular triangular matrix
     * @return determinant for the given SLAE
     */
    fun determinant(triangular: Array<DoubleArray>): Double

    /**
     * Creates triangular matrix for the given SLAE.
     * @return triangular matrix for the give SLAE
     */
    fun triangular(): Array<DoubleArray>

    /**
     * Finds roots for the given SLAE using the Gaussian method.
     * @param triangular triangular matrix
     * @return list of roots for the given SLAE
     */
    fun roots(triangular: Array<DoubleArray>): DoubleArray

    /**
     * Finds residuals for the given SLAE.
     * @param triangular triangular matrix
     * @return list of residuals for the given SLAE
     */
    fun residuals(triangular: Array<DoubleArray>): DoubleArray
}