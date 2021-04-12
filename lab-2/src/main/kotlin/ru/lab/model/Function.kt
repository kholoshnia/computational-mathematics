package ru.lab.model

import net.objecthunter.exp4j.Expression

class Function(private val expression: Expression) {
    companion object {
        const val VARIABLE_NAME = "x"
    }

    operator fun invoke(x: Double): Double {
        return expression
            .setVariable(VARIABLE_NAME, x)
            .evaluate()
    }

    fun derivative(x: Double, accuracy: Double): Double {
        return (invoke(x + accuracy) - invoke(x)) / accuracy
    }
}