import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

abstract class Action(val a: Double, val b: Double) {
    abstract fun calculate(): Double
}

class SumAction(a: Double, b: Double) : Action(a, b) {
    override fun calculate(): Double {
        return a + b
    }
}

enum class ActionType(val s: String) {
    SUM("sum");

    companion object {
        fun getInstance(s: String): ActionType? {
            values().forEach { value ->
                if (value.s == s) {
                    return value
                }
            }
            return null;
        }
    }
}

class Calculator {
    private val actions: Map<ActionType, KClass<out Action>> = mapOf(ActionType.SUM to SumAction::class);

    fun calculate(a: Double, b: Double, action: ActionType?): Double? {
        val constructor = actions[action]?.primaryConstructor
        return if (constructor != null && constructor.parameters.isNotEmpty())
            constructor.call(a, b).calculate() else null;
    }
}


fun main(args: Array<String>) {
    val calculator = Calculator()
    val result = calculator.calculate(args[0].toDouble(), args[1].toDouble(), ActionType.getInstance(args[2]));
    println(result)
}