package org.skycastle.util.grid

import org.skycastle.util.Vec3i

/**
 * A 3D position within a grid with some specified gridsize.
 */
case class GridPos(gridSize: GridSize, pos: Vec3i) {

  /**
   * Assumes the gridSized cube that the position indicates.
   */
  def bounds: GridBounds = GridBounds(gridSize, pos, pos + Vec3i.ONES)

  /**
   * Grid in larger grid that contains this grid cell
   */
  def parentPos: GridPos = GridPos(gridSize.largerSize, pos / 2)

  /**
   * The grids in the next more detailed grid that are contained in this grid cell.
   */
  def childPositions: List[GridPos] = {
    val smallerSize: GridSize = gridSize.smallerSize
    val basePos: Vec3i = pos * 2
    Vec3i.ONE_PERMUTATIONS map {corner => GridPos(smallerSize, basePos + corner)}
  }

  /**
   * True if this pos is inside the specified cell, or equal to it.
   */
  def isInside(other: GridPos): Boolean = {
    other.gridSize <= this.gridSize &&
    // TODO: Math
  }

  def gridHash(rootSize: GridSize): List[GridPos] = {
    if (gridSize < rootSize) this :: parentPos.gridHash(rootSize)
    else if (gridSize == rootSize) this :: Nil
    else throw new UnsupportedOperationException("Hash pos not supported for greater sizes than root size.")
  }


}
