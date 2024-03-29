package ru.lab

import ru.lab.calculator.SlaeCalculator
import ru.lab.calculator.SlaeCalculatorImpl
import ru.lab.terminal.Terminal
import ru.lab.terminal.TerminalException
import ru.lab.terminal.TerminalImpl

fun main(args: Array<String>) {
    try {
        val terminal: Terminal = TerminalImpl(args)
        terminal.printGreetings()
        val slaeMatrix = terminal.readSlaeMatrix()
        terminal.printEntered(slaeMatrix)
        val slaeCalculator: SlaeCalculator = SlaeCalculatorImpl(slaeMatrix)

        val triangular = slaeCalculator.triangular()
        val determinant = slaeCalculator.determinant(triangular)
        terminal.printResults()
        terminal.printDeterminant(determinant)
        terminal.printTriangular(triangular)

        if (determinant != 0.0) {
            val roots = slaeCalculator.roots(triangular)
            val residuals = slaeCalculator.residuals(roots)
            terminal.printRoots(roots)
            terminal.printResiduals(residuals)
        } else {
            terminal.printInfiniteOrInconsistent()
        }

        terminal.printSeparator()
    } catch (e: TerminalException) {
        println(e.message)
    }
}
