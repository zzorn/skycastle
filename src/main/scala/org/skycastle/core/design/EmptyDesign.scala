package org.skycastle.core.design

/**
 * An empty design, for use where a design is expected but nothing should be created.
 */
object EmptyDesign extends Design {
  def create() = null
}