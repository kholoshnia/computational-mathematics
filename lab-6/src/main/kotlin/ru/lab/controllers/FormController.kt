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

    fun getOde() = if (formView.odeTextField.text.isNullOrBlank())
        throw IllegalArgumentException("ODE required")
    else functionController.getOde(formView.odeTextField.text)

    fun getSolution() = if (formView.solutionTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Solution required")
    else functionController.getFunction(formView.solutionTextField.text)

    fun getInitialY() = if (formView.initialYTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Initial y required")
    else toDouble(formView.initialYTextField.text)

    fun getLeftBoundary() = if (formView.leftBoundaryTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Left boundary required")
    else toDouble(formView.leftBoundaryTextField.text)

    fun getRightBoundary() = if (formView.rightBoundaryTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Right boundary required")
    else toDouble(formView.rightBoundaryTextField.text)

    fun getStep() = if (formView.stepTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Step required")
    else toDouble(formView.stepTextField.text)

    fun getAccuracy() = if (formView.accuracyTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Accuracy required")
    else toDouble(formView.accuracyTextField.text)

    fun getMethod() = Method.find(formView.methodComboBox.value)!!

    fun getDrawStep() = if (formView.drawStepTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Draw step required")
    else toDouble(formView.drawStepTextField.text)
}