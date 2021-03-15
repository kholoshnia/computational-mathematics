package ru.lab.terminal

import java.io.File
import java.util.*


/**
 * Terminal implementation.
 * @see Terminal
 */
class TerminalImpl(private val args: Array<String>) : Terminal {
    /**
     * Opens file and checks for errors.
     * @param path file path
     * @return file
     */
    private fun openFile(path: String): File {
        val file = File(path)

        if (!file.exists()) throw TerminalException("$path does not exist")
        if (!file.isFile) throw TerminalException("$path is not a file")
        if (!listOf("txt", "md", "csv").contains(file.extension))
            throw TerminalException("${file.extension} is not supported")
        if (!file.canRead()) throw TerminalException("Cannot read from file $path (check permissions)")

        return file
    }

    /**
     * Reads int value.
     * @param prompt user prompt
     * @return int value
     */
    private fun readInt(prompt: String): Int {
        var value: Int?
        do {
            print(prompt)
            value = try {
                readLine()?.toInt()
            } catch (e: NumberFormatException) {
                println("Integer number required!")
                null
            }
        } while (value == null)
        return value
    }

    /**
     * Reads double value.
     * @param prompt user prompt
     * @return double value
     */
    private fun readDouble(prompt: String): Double {
        var value: Double?
        do {
            print(prompt)
            value = try {
                readLine()?.replace(",", ".")?.toDouble()
            } catch (e: NumberFormatException) {
                println("Real number required!")
                null
            }
        } while (value == null)
        return value
    }

    /**
     * Reads matrix from console.
     * @return read matrix
     */
    private fun fromConsole(): Array<DoubleArray> {
        val size = readInt("Enter matrix size: ")
        if (size <= 1 || size > 20) throw TerminalException("Matrix size must be > 1 and <= 20")
        printSeparator()

        println("Enter matrix coefficients:")
        val slaeMatrix: Array<DoubleArray> = Array(size) { DoubleArray(size + 1) }

        for (j in (0 until size)) {
            for (i in (0 until size)) {
                slaeMatrix[j][i] = readDouble("a(${j + 1},${i + 1})=")
            }
            slaeMatrix[j][size] = readDouble("b(${j + 1},${size + 1})=")
        }

        return slaeMatrix
    }

    /**
     * Reads matrix from file.
     * @param path file path
     * @return read matrix
     */
    private fun fromFile(path: String): Array<DoubleArray> {
        println("Reading SLAE matrix from file...")
        val file = openFile(path)

        file.bufferedReader().use { f ->
            val size: Int
            try {
                size = f.readLine().toInt()
            } catch (e: NumberFormatException) {
                throw TerminalException("Matrix size must integer!")
            }
            if (size <= 1 || size > 20) throw TerminalException("Matrix size must be > 1 and <= 20")

            val slaeMatrix: Array<DoubleArray> = Array(size) { DoubleArray(size + 1) }

            for (j in (0 until size)) {
                val rowString = f.readLine()
                if (rowString.isNullOrBlank()) throw TerminalException("Wrong matrix format!")
                val rowStringSplit = rowString.split(" +".toRegex())
                if (rowStringSplit.size != size + 1) throw TerminalException("Wrong matrix size in file")
                slaeMatrix[j] = rowString.split(" +".toRegex()).map { el ->
                    try {
                        el.toDouble()
                    } catch (e: NumberFormatException) {
                        throw TerminalException("Wrong matrix format!")
                    }
                }.toDoubleArray()
            }
            return slaeMatrix
        }
    }

    override fun readSlaeMatrix(): Array<DoubleArray> {
        if (args.isEmpty()) return fromConsole()
        return fromFile(args[0])
    }

    override fun printGreetings() {
        println("-----------< SLAE Calculator >----------")
    }

    override fun printResults() {
        println("---------------< Results >--------------")
    }

    override fun printInfiniteOrInconsistent() {
        println("SLAE has an infinite set of roots or is inconsistent")
    }

    override fun printSeparator() {
        println("----------------------------------------")
    }

    /**
     * Formats double to string.
     * @param double double value
     * @return string value
     */
    private fun formatDouble(double: Double, accuracy: Int = 2): String {
        return "%.${accuracy}f".format(Locale.US, double)
    }

    /**
     * Prints SLAE matrix to the console.
     * @param slaeMatrix SLAE matrix
     */
    private fun printSlaeMatrix(slaeMatrix: Array<DoubleArray>) {
        slaeMatrix.forEach { row ->
            print("|\t")
            for (i in (0..row.size - 2)) {
                print("${formatDouble(row[i])}\t")
            }
            println("|\t${formatDouble(row.last())}\t|")
        }
    }

    override fun printEntered(slaeMatrix: Array<DoubleArray>) {
        println("-----------< Entered matrix >-----------")
        printSlaeMatrix(slaeMatrix)
    }

    override fun printTriangular(triangular: Array<DoubleArray>) {
        println("Triangular matrix:")
        printSlaeMatrix(triangular)
    }

    override fun printRoots(roots: DoubleArray) {
        println("Roots vector: ${roots.joinToString(" ") { el -> formatDouble(el) }}")
    }

    override fun printResiduals(residuals: DoubleArray) {
        println("Residuals vector: ${residuals.joinToString(" ") { el -> formatDouble(el, 5) }}")
    }

    override fun printDeterminant(determinant: Double) {
        println("Determinant: ${formatDouble(determinant)}")
    }
}
