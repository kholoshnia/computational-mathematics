package ru.lab.calculator

data class Result(
    var determinant: Double,
    var triangular: List<List<Double>>,
    var roots: List<Double>,
    var residuals: List<Double>
)