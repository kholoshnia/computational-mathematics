class Calculator {
    fun sum(a: Int, b: Int): Int {
        return a + b
    }
}

fun main(args: Array<String>) {
    val calculator = Calculator()
    val result = String.format("%s %s", args.joinToString(" "), calculator.sum(2, 2))
    println(result)
}