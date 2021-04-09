package ru.lab.domain

import net.objecthunter.exp4j.Expression


class Function(private val expression: Expression) {
    companion object {
        const val X_VARIABLE = "x"
    }

    operator fun invoke(x: Double): Double {
        return try {
            expression
                .setVariable(X_VARIABLE, x)
                .evaluate()
        } catch (e: ArithmeticException) {
            Double.NaN
        }
    }
}