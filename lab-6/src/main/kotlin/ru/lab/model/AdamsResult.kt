package ru.lab.model

data class AdamsResult(
    val i: Int,
    val xValues: List<Double>,
    val yValues: List<Double>,
    val pred: Double,
    val corr: Double,
)