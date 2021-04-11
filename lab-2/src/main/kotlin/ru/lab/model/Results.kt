package ru.lab.model

data class Results(
    val roots: DoubleArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Results

        if (!roots.contentEquals(other.roots)) return false

        return true
    }

    override fun hashCode(): Int {
        return roots.contentHashCode()
    }
}