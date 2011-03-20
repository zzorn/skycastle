package org.skycastle.core.design

import grid.GridSize

/**
 * A component of a design.
 * Has some shape and outside dimensions.
 */
trait Part {

  def gridSize: GridSize
  def outerBounds: GridBounds
  def occupiedCells: Iterator[GridPos]

}