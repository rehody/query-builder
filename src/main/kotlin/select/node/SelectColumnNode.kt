package select.node

data class SelectColumnNode(
    val columns: List<SelectColumn>
) : SelectNode
