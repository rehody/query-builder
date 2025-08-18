package select.step

import common.step.Step

interface FromStep : Step {
    fun from(table: String): SelectWhereStep<OrderStep>
}