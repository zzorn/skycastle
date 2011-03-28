package org.skycastle.util.octree

import com.jme3.math.Vector3f
import org.skycastle.util.Vec3i
import org.skycastle.util.grid.{GridBounds, GridPos}

/**
 * Octal tree cell that stores some kind of parts at integer grid positions.
 */
class OctTree[T](pos: GridPos) {

  private var _part: T = null.asInstanceOf[T]
  private var _children: Array[OctTree[T]] = null.asInstanceOf[Array[OctTree[T]]]
  private var numberOfParts = 0 // Number of parts in this cell + all child cells.  Used to quickly check if a cell can be removed

  def childIndex(p: Vector3f): Int = {
    val mid = pos.center
    (if (p.x < mid.x) 0 else 1) +
    (if (p.y < mid.y) 0 else 2) +
    (if (p.z < mid.z) 0 else 4)
  }

  def childPos(p: Vector3f): GridPos = {
    val mid = pos.center
    val childPos = Vec3i(
      pos.pos.x * 2 + (if (p.x < mid.x) 0 else 1),
      pos.pos.y * 2 + (if (p.y < mid.y) 0 else 1),
      pos.pos.z * 2 + (if (p.z < mid.z) 0 else 1))
    GridPos(pos.gridSize.smallerSize, childPos)
  }

  def hasParts: Boolean = numberOfParts > 0

  /**
   * Returns true if a part was removed.
   */
  def removePartAt(at: GridPos): Boolean = {
    if (at.gridSize >= pos.gridSize) throw new IllegalArgumentException("Can not remove part outside root cell")
    else if (at == pos) {
      if (_part != null) {
        _part = null.asInstanceOf[T]
        numberOfParts -= 1
        true
      }
      else false
    }
    else if (_children != null) {
      val index = childIndex(at.center)
      val child = _children(index)
      if (child != null) {
        val removed = child.removePartAt(at)
        if (removed) numberOfParts -= 1

        // If the child is left with no parts delete the cell
        if (!child.hasParts) _children(index) = null.asInstanceOf[OctTree[T]]
        removed
      }
      else false
    }
    else false
  }

  /**
   * Returns change in number of parts.
   */
  def setPartAt(at: GridPos, part: T): Int = {
    // Check if self
    if (at == pos) {
      _part = part
      _children = null // The part replaces any children in this node
      val partNumberChange = 1 - numberOfParts
      numberOfParts = 1
      partNumberChange
    }
    // Check if outside
    else if (at.gridSize >= pos.gridSize) throw new IllegalArgumentException("Can not add part outside root cell")
    else {
      // Add to child
      val i = childIndex(at.center)

      // Create child array if needed
      if (_children == null) _children = new Array[OctTree[T]](8)

      var cell = _children(i)

      // Create child cell if needed
      if (cell == null) {
        cell = new OctTree(childPos(at.center))
        _children(i) = cell
      }

      // Delegate to child
      val partDelta = cell.setPartAt(at, part)
      numberOfParts += partDelta
      partDelta
    }
  }

  def getPartAt(at: GridPos): Option[T] = {
    if (at == pos) return if (_part == null) None else Some(_part)
    else if (at.gridSize > pos.gridSize) None
    else if (_children != null && at.isInside(pos)) {
      // Check child
      val child = _children(childIndex(at.center))
      if (child != null) child.getPartAt(at)
      else None
    }
    else None
  }

  def hasPartAt(at: GridPos): Boolean = getPartAt(at).isDefined

  def getPartsOverlapping(bounds: GridBounds): List[T] = {
    if (!bounds.overlaps(pos)) Nil
    else {
      if (_part != null) List(_part)
      else if (_children == null) Nil
      else {
        _children.toList.flatMap(c => if (c == null) Nil else c.getPartsOverlapping(bounds))
      }
    }
  }
}
