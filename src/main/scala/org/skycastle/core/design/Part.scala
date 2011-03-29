package org.skycastle.core.design

import org.skycastle.util.grid.{GridSize, GridBounds, GridPos}
import org.skycastle.util.Vec3i

/**
 * A component of a design.
 * Has some shape and outside dimensions.
 */
trait Part {

  def anchorPos: GridPos
  def gridSize: GridSize
  def outerBounds: GridBounds
  def occupiedCells: Iterator[GridPos]

}