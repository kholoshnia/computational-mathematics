package ru.lab.controllers

import ru.lab.model.Method
import ru.lab.model.Variant
import ru.lab.views.FormView
import tornadofx.Controller


class FormController : Controller() {
    private val formView: FormView by inject()
    private val functionController: FunctionController by inject()

    private fun toDouble(string: String) = string
        .replace(",", ".")
        .toDouble()

    private fun toDoubleList(string: String) = string.split(" +".toRegex())
        .map { toDouble(it) }
        .toList()

    fun getVariant() = Variant.find(formView.variantComboBox.value)

    fun getXValues() = if (formView.xValuesTextField.text.isNullOrBlank())
        throw IllegalArgumentException("X values required")
    else toDoubleList(formView.xValuesTextField.text)

    fun getYValues() = if (formView.yValuesTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Y values required")
    else toDoubleList(formView.yValuesTextField.text)

    fun getFunction() = if (formView.functionTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Function required")
    else functionController.getFunction(formView.functionTextField.text)

    fun getLeftBoundary() = if (formView.leftBoundaryTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Left boundary required")
    else formView.leftBoundaryTextField.text.toDouble()

    fun getRightBoundary() = if (formView.rightBoundaryTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Right boundary required")
    else formView.rightBoundaryTextField.text.toDouble()

    fun getPartitioning() = if (formView.partitioningTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Partitioning required")
    else formView.partitioningTextField.text.toDouble()

    fun getMethod() = Method.find(formView.methodComboBox.value)

    fun getSearchValue() = if (formView.searchValueTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Search value required")
    else toDouble(formView.searchValueTextField.text)

    fun getStep() = if (formView.stepTextField.text.isNullOrBlank())
        throw IllegalArgumentException("Step required")
    else toDouble(formView.stepTextField.text)
}