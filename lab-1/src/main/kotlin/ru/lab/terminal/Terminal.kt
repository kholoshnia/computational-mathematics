package ru.lab.terminal

/**
 * Terminal interface provides methods for reading SLAE
 * and writing result of the calculations.
 */
interface Terminal {
    /**
     * Reads SLAE matrix from terminal or file.
     * @return read SLAE matrix
     */
    fun readSlaeMatrix(): Array<DoubleArray>

    /** Prints greetings message. */
    fun printGreetings()

    /** Prints results message. */
    fun printResults()

    /** Prints separator message. */
    fun printSeparator()

    /**
     * Prints entered SLAE matrix.
     * @param slaeMatrix SLAE matrix
     */
    fun printEntered(slaeMatrix: Array<DoubleArray>)

    /**
     * Prints triangular matrix.
     * @param triangular triangular matrix
     */
    fun printTriangular(triangular: Array<DoubleArray>)

    /**
     * Prints roots.
     * @param roots SLAE roots
     */
    fun printRoots(roots: DoubleArray)

    /**
     * Prints residuals.
     * @param residuals SLAE residuals
     */
    fun printResiduals(residuals: DoubleArray)

    /**
     * Prints determinant
     * @param determinant SLAE determinant
     */
    fun printDeterminant(determinant: Double)
}