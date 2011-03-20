package org.skycastle.core.design

import grid.{Vec3i, GridSize}

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

  def gridHashList(rootSize: GridSize): List[GridPos] = {
    if (gridSize < rootSize) this :: parentPos.gridHashList(rootSize)
    else if (gridSize == rootSize) this :: Nil
    else throw new UnsupportedOperationException("Hash pos not supported for greater sizes than root size.")
  }


}