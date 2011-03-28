package org.skycastle.util.octree

import java.util.HashMap
import org.skycastle.util.Vec3i
import org.skycastle.util.grid.{GridBounds, GridPos, GridSize}

/**
 * Stores objects with a geo-hash type storage based on a position, with an oct-tree at each position..
 */
class GridStorage[T](rootSize: GridSize) {

  private var geoHash: HashMap[Vec3i, OctTree[T]] = new HashMap[Vec3i, OctTree[T]]()

  private def getOctTreeForPos(pos: GridPos): OctTree[T] = {
    if (pos.gridSize > rootSize) throw new IllegalArgumentException("Can not store at larger grid size than the root grid size")
    else {
      val treePos = pos.ancestorPos(rootSize)

      // Get oct-tree
      var tree: OctTree[T] = geoHash.get(treePos.pos)
      if (tree == null) {
        tree = new OctTree[T](treePos)
        geoHash.put(treePos.pos, tree)
      }

      tree
    }
  }

  def put(pos: GridPos, data: T) = getOctTreeForPos(pos).setPartAt(pos, data)
  def remove(pos: GridPos) = getOctTreeForPos(pos).removePartAt(pos)
  def getDataAt(pos: GridPos): Option[T] = getOctTreeForPos(pos).getPartAt(pos)
  def hasDataAt(pos: GridPos): Boolean = getOctTreeForPos(pos).hasPartAt(pos)

  def getDataOverlapping(bounds: GridBounds): List[T] = {
    bounds.ancestorBounds(rootSize).gridPositions flatMap {treePos =>
      getOctTreeForPos(treePos).getPartsOverlapping(bounds)
    }
  }


}