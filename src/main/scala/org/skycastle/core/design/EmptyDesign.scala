package org.skycastle.core.design

import org.skycastle.util.parameters.Parameters
import org.skycastle.core.entity.EmptyEntity

/**
 * An empty design, for use where a design is expected but nothing should be created.
 */
object EmptyDesign extends Design {
  def build(context: Parameters) = EmptyEntity
}