package org.skycastle.util.grid

import org.skycastle.util.Vec3i
import com.jme3.math.Vector3f

/**
 * A 3D position within a grid with some specified gridsize.
 */
case class GridPos(gridSize: GridSize, pos: Vec3i) extends GridBounds(gridSize, pos, pos + Vec3i.ONES) {

  /**
   * True if this pos is inside the specified cell, or equal to it.
   */
  def isInside(other: GridPos): Boolean = {
    other.gridSize <= this.gridSize && other.contains(this.center)
  }

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
   * The grid pos that this position is in at the specified grid size.
   */
  def ancestorPos(rootSize: GridSize): GridPos = {
    if (gridSize == rootSize) this
    else if (rootSize < gridSize) throw new UnsupportedOperationException("Ancestor pos not supported for smaller sizes than own size.")
    else parentPos.ancestorPos(rootSize)
  }




}
