package condition.step

interface LogicalStep {
    fun and(column: String): ComparisonStep
    fun or(column: String): ComparisonStep

    fun and(block: ColumnStep.() -> LogicalStep): LogicalStep
    fun or(block: ColumnStep.() -> LogicalStep): LogicalStep
}
