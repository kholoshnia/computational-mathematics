package ru.lab.controllers

interface Approximation {
    fun getFunction(
        xValues: List<Double>,
        yValues: List<Double>
    ): String
}