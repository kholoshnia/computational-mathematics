package ru.lab.model

import net.objecthunter.exp4j.Expression

class Function(private val expression: Expression) {
    companion object {
        const val VARIABLE_NAME = "x"
    }

    operator fun invoke(x: Double): Double {
        return try {
            expression
                .setVariable(VARIABLE_NAME, x)
                .evaluate()
        } catch (e: ArithmeticException) {
            Double.NaN
        }
    }
}