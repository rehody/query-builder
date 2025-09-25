package insert.step

import common.step.BuildStep
import common.step.Step
import insert.node.ValueNode

interface ValueStep : Step {
    fun values(vararg values: ValueNode): BuildStep
}