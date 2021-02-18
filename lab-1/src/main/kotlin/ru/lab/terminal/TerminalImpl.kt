package ru.lab.terminal

import ru.lab.calculator.Result
import ru.lab.terminal.file.FileManager
import ru.lab.terminal.file.FileManagerException

/**
 * Terminal implementation.
 * @see Terminal
 */
class TerminalImpl(private val args: Array<String>) : Terminal {
    /**
     * Fills empty matrix coefficients with zeros.
     * @param matrix matrix
     * @return normalized matrix
     */
    private fun normalize(matrix: List<List<Double>>): List<List<Double>> {
        val maxRow = matrix.maxByOrNull { row -> row.size }
            ?: throw TerminalException("Normalizing error")

        val normalized: MutableList<MutableList<Double>> = mutableListOf()

        matrix.forEach { row ->
            while (row.size < maxRow.size) {
                for (i in row.size + 1..maxRow.size) {
                    normalized[i].add(0.0)
                }
            }
        }

        return normalized
    }

    /**
     * Reads matrix from file.
     * @param path file path
     * @return read matrix
     */
    private fun fromFile(path: String): List<List<Double>> {
        val file = try {
            FileManager().openFile(path)
        } catch (e: FileManagerException) {
            throw TerminalException(e)
        }

        val matrix: MutableList<List<Double>> = mutableListOf()

        file.useLines { lines ->
            lines.forEach { line ->
                matrix.add(line.split(" +".toRegex()).map { el ->
                    try {
                        el.toDouble()
                    } catch (e: NumberFormatException) {
                        throw TerminalException("Invalid matrix coefficient format: $el")
                    }
                }.toDoubleArray().toList())
            }
        }

        if (matrix.isEmpty()) throw TerminalException("Empty matrix read")
        return normalize(matrix)
    }

    /**
     * Reads matrix from console.
     * @return read matrix
     */
    private fun fromConsole(): List<List<Double>> {
        println("Please, enter matrix coefficients and hit Ctrl+D when done:")

        val matrix: MutableList<List<Double>> = mutableListOf()
        var line = readLine()

        while (line != "done") {
            if (line == null) break
            matrix.add(line.split(" +".toRegex()).map { el ->
                try {
                    el.toDouble()
                } catch (e: NumberFormatException) {
                    throw TerminalException("Invalid matrix coefficient format: $el")
                }
            }.toDoubleArray().toList())
            line = readLine()
        }

        if (matrix.isEmpty()) throw TerminalException("Empty matrix entered")
        return normalize(matrix)
    }

    override fun readSlae(): List<List<Double>> {
        if (args.isEmpty()) return fromConsole()
        return fromFile(args.joinToString(" "))
    }

    override fun writeResult(result: Result) {
        println("Determinant: ${result.determinant}")
        println("Triangular matrix:")
        println(result.triangular.joinToString("\n") { row ->
            row.joinToString(" ")
        })
        println("Roots vector: ${result.roots.joinToString(" ")}")
        println("Residuals vector: ${result.residuals.joinToString(" ")}")
    }
}