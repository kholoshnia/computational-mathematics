package ru.lab.model

data class Results(
    val name: String,
    val approximation: String,
    val deviationMeasure: String,
    val rmsDeviation: String,
    val rSquare: String,
    val other: String
)