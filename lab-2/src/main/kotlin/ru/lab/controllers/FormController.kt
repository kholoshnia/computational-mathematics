package ru.lab.controllers

import ru.lab.model.Function
import ru.lab.model.Method
import ru.lab.views.FormView
import tornadofx.Controller

class FormController : Controller() {
    private val formView: FormView by inject()
    private val functionController: FunctionController by inject()

    private fun toDouble(string: String): Double {
        return string
            .replace(",", ".")
            .toDouble()
    }

    fun getFunction(): Function {
        return functionController.getFunction(formView.functionTextField.text)
    }

    fun getLeftBoundary(): Double {
        return toDouble(formView.leftTextField.text)
    }

    fun getRightBoundary(): Double {
        return toDouble(formView.rightTextField.text)
    }

    fun getAccuracy(): Double {
        return toDouble(formView.accuracyTextField.text)
    }

    fun getStep(): Double {
        val step = toDouble(formView.stepTextField.text)
        return if (step < 0.01) 0.01 else step
    }

    fun getMethod(): Method {
        return Method.find(formView.methodComboBox.value) ?: Method.HALF_DIVISION
    }
}