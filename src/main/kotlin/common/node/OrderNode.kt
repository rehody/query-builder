package common.node

import common.step.Step

data class OrderNode(
    val columns: List<OrderColumnNode>
) : Step
