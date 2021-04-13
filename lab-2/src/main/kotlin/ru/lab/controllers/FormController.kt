package ru.lab.controllers

import ru.lab.model.Method
import ru.lab.views.FormView
import tornadofx.Controller

class FormController : Controller() {
    private val formView: FormView by inject()
    private val functionController: FunctionController by inject()

    private fun toDouble(string: String) = string
        .replace(",", ".")
        .toDouble()

    fun getFunction() = functionController.getFunction(formView.functionTextField.text)

    fun getLeftBoundary() = toDouble(formView.leftTextField.text)

    fun getRightBoundary() = toDouble(formView.rightTextField.text)

    fun getAccuracy() = toDouble(formView.accuracyTextField.text)

    fun getStep(): Double {
        val step = toDouble(formView.stepTextField.text)
        return if (step < 0.01) 0.01 else step
    }

    fun getMethod() = Method.find(formView.methodComboBox.value) ?: Method.HALF_DIVISION
}