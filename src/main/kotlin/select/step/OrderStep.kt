package select.step

import common.node.OrderColumnNode
import common.step.BuildStep
import common.step.Step


interface OrderStep : Step, LimitStep, BuildStep {
    fun orderBy(vararg columns: OrderColumnNode): LimitStep
}