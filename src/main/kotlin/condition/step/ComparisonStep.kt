package condition.step

import common.step.Step

interface ComparisonStep : Step {
    fun eq(value: Any?): LogicalStep
    fun neq(value: Any?): LogicalStep
    fun gt(value: Any?): LogicalStep
    fun lt(value: Any?): LogicalStep
    fun <T> between(from: T, to: T): LogicalStep
}
