package org.skycastle.core.design

import org.scalaprops.Bean
import org.skycastle.core.entity.{Entity, Facet}

/**
 * Represents a design for some in-game construction.
 */
// TODO: Do these need to be beans or could normal reflection be used to load and edit them?
trait Design extends Bean {

  // Exponent for the grid scale.  Grid cell size for the component is calculated as 1m * 2^exponent.
  val gridExponent = p('gridExponent, 0)

  // Size in grid cells (outer size)
  val width = p('width, 1)
  val height = p('height, 1)
  val depth = p('depth, 1)

  /**
   * Create the construct that this design represents.
   */
  def create():  Entity


}