package org.skycastle.core.proceduraldesign

import org.skycastle.util.parameters.Parameters
import org.skycastle.core.entity.NoEntity

/**
 * An empty design, for use where a design is expected but nothing should be created.
 */
object EmptyDesign extends ProceduralDesign {
  def build(context: Parameters) = NoEntity
}