package ru.lab.model.results

data class NewtonResults(
    var n: Int,
    var xk: Double,
    var fxk: Double,
    var dxk: Double,
    var xkNext: Double,
    var xkXkNext: Double,
)