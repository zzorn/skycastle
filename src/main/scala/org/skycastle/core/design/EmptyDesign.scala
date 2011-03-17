package org.skycastle.core.design

import org.skycastle.util.parameters.Parameters

/**
 * An empty design, for use where a design is expected but nothing should be created.
 */
object EmptyDesign extends Design {
  def create(context: Parameters) = null
}