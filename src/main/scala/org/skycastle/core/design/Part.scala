package org.skycastle.core.design

import org.skycastle.util.grid.{GridSize, GridBounds, GridPos}

/**
 * A component of a design.
 * Has some shape and outside dimensions.
 */
trait Part {

  def gridSize: GridSize
  def outerBounds: GridBounds
  def occupiedCells: Iterator[GridPos]

}