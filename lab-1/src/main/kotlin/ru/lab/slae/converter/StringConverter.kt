package ru.lab.slae.converter

class StringConverter : Converter<String> {
    val superscriptIndexMap: Map<String, String> = mapOf(
        "0" to "⁰", "1" to "¹", "2" to "²", "3" to "³", "4" to "⁴",
        "5" to "⁵", "6" to "⁶", "7" to "⁷", "8" to "⁸", "9" to "⁹"
    )

    val subscriptIndexMap: Map<String, String> = mapOf(
        "0" to "₀", "1" to "₁", "2" to "₂", "3" to "₃", "4" to "₄",
        "5" to "₅", "6" to "₆", "7" to "₇", "8" to "₈", "9" to "₉"
    )

    override fun convertMatrix(matrix: Array<DoubleArray>): String {
        return matrix.joinToString("\n") { row ->
            row.joinToString(" ")
        }
    }

    override fun convertRoots(roots: DoubleArray): String {
        return roots.joinToString(" ")
    }

    override fun convertResiduals(residuals: DoubleArray): String {
        return residuals.joinToString(" ")
    }
}