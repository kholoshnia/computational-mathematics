package ru.lab.slae.converter

import ru.lab.slae.Slae

interface Converter<T> {
    fun convertMatrix(matrix: Array<DoubleArray>): T
    fun convertRoots(roots: DoubleArray): T
    fun convertResiduals(residuals: DoubleArray): T
}