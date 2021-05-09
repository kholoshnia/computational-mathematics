package ru.lab.controllers

import ru.lab.views.FormView
import tornadofx.Controller


class FormController : Controller() {
    private val formView: FormView by inject()

    private fun toDouble(string: String) = string
        .replace(",", ".")
        .toDouble()

    private fun toDoubleList(string: String) = string.split(" +".toRegex())
        .map { toDouble(it) }
        .toList()

    fun getLeftBoundary(): Double? {
        return if (formView.leftTextField.text.isNullOrBlank()) null
        else toDouble(formView.leftTextField.text)
    }

    fun getRightBoundary(): Double? {
        return if (formView.rightTextField.text.isNullOrBlank()) null
        else toDouble(formView.rightTextField.text)
    }

    fun getStep() = toDouble(formView.stepTextField.text)

    fun getXValues() = toDoubleList(formView.xValuesTextField.text)

    fun getYValues() = toDoubleList(formView.yValuesTextField.text)
}