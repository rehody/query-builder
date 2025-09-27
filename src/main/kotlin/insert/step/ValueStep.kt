package insert.step

import common.step.Step
import insert.node.ValueNode

interface ValueStep<NEXT : Step> : Step {
    fun values(vararg values: ValueNode): NEXT
}