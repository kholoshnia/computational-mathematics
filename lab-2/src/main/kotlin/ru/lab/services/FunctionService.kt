package ru.lab.services

import net.objecthunter.exp4j.ExpressionBuilder
import ru.lab.domain.Function

class FunctionService {
    fun getFunction(functionString: String): Function {
        val function = functionString.replace(",", ".")
        val expression = ExpressionBuilder(function)
            .variable(Function.X_VARIABLE)
            .build()
        return Function(expression)
    }
}