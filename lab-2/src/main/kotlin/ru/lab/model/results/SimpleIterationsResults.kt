package ru.lab.model.results

data class SimpleIterationsResults(
    var n: Int,
    var xk: Double,
    var fxk: Double,
    var xkNext: Double,
    var fiXk: Double,
    var xkXkNext: Double,
)