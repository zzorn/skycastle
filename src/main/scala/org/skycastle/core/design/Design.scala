package org.skycastle.core.design

import org.scalaprops.Bean
import org.skycastle.core.entity.{Entity, Facet}
import org.skycastle.util.parameters.Parameters

/**
 * Represents a design for some in-game construction.
 */
trait Design {

  // Exponent for the grid scale.  Grid cell size for the component is calculated as 1m * 2^exponent.
  def gridExponent = 0
  final def gridCellSize: Float = math.pow(2, gridExponent).toFloat

  // Size in grid cells (outer size along x, y, z axis in grid cells)
  def gridBounds: (Int, Int, Int) = (1,1,1)
  final def bounds: (Float, Float, Float) = {
    val grids = gridBounds
    val gridSize = gridCellSize
    ( gridSize * grids._1,
      gridSize * grids._2,
      gridSize * grids._3)
  }

  /**
   * Create the construct that this design represents.
   */
  def create(context: Parameters):  Entity


}