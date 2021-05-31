package ru.lab.model

import net.objecthunter.exp4j.Expression


class Ode(private val expression: Expression) {
    companion object {
        const val X_VARIABLE_NAME = "x"
        const val Y_VARIABLE_NAME = "y"
    }

    operator fun invoke(x: Double, y: Double): Double {
        return expression
            .setVariable(X_VARIABLE_NAME, x)
            .setVariable(Y_VARIABLE_NAME, y)
            .evaluate()
    }
}