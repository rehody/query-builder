package common.node

import util.OrderDirection

data class OrderColumnNode(
    val name: String,
    val direction: OrderDirection
) : Column
