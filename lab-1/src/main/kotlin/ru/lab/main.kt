package ru.lab

import ru.lab.calculator.SlaeCalculator
import ru.lab.calculator.SlaeCalculatorImpl
import ru.lab.terminal.Terminal
import ru.lab.terminal.TerminalImpl

fun main(args: Array<String>) {
    try {
        val terminal: Terminal = TerminalImpl(args)
        val matrix = terminal.readSlae()
        val matrixCalculator: SlaeCalculator = SlaeCalculatorImpl(matrix)
        val result = matrixCalculator.calculate()
        terminal.writeResult(result)
    } catch (e: Exception) {
        print(e.message)
        return
    }
}