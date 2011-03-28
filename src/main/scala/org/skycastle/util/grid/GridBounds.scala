package org.skycastle.util.grid

import com.jme3.math.Vector3f
import math._
import org.skycastle.util.Vec3i


/**
 * 
 */
class GridBounds(gridSize: GridSize, a: Vec3i, b: Vec3i) {

  lazy val min: Vector3f = {
    val s = gridSize.gridSizeMeters
    new Vector3f(s * (a.x min b.x).toFloat,
                 s * (a.y min b.y).toFloat,
                 s * (a.z min b.z).toFloat)
  }

  lazy val max: Vector3f = {
    val s = gridSize.gridSizeMeters
    new Vector3f(s * (a.x max b.x).toFloat,
                 s * (a.y max b.y).toFloat,
                 s * (a.z max b.z).toFloat)
  }

  lazy val center: Vector3f = {
    val scale = gridSize.gridSizeMeters
    new Vector3f(scale * (a.x + b.x / 2f),
                 scale * (a.y + b.y / 2f),
                 scale * (a.z + b.z / 2f)) 
  }

  def contains(point: Vector3f): Boolean = {
    val thisMin = min
    val thisMax = max

    thisMin.x <= point.x && point.x < thisMax.x &&
    thisMin.y <= point.y && point.y < thisMax.y &&
    thisMin.z <= point.z && point.z < thisMax.z
  }

  def minX: Int = a.x min b.x
  def maxX: Int = a.x max b.x
  def minY: Int = a.y min b.y
  def maxY: Int = a.y max b.y
  def minZ: Int = a.z min b.z
  def maxZ: Int = a.z max b.z

  def ancestorBounds(rootGridSize: GridSize): GridBounds = {
    new GridBounds(rootGridSize,
      GridPos(gridSize, a).ancestorPos(rootGridSize).pos,
      GridPos(gridSize, b).ancestorPos(rootGridSize).pos)
  }

  def iterateGridPos: Iterator[GridPos] = {
    for (z <- minZ.until(maxZ).toIterator;
         y <- minY.until(maxY).toIterator;
         x <- minX.until(maxX).toIterator)
      yield GridPos(gridSize, Vec3i(x, y, z))
  }

  def gridPositions: List[GridPos] = iterateGridPos.toList

  def overlaps(other: GridBounds): Boolean = {
    val otherMax = other.max
    val otherMin = other.min
    val thisMin = min
    val thisMax = max

    if (thisMin.x > otherMax.x || thisMax.x < otherMin.x) false
    else if (thisMin.y > otherMax.y || thisMax.y < otherMin.y) false
    else if (thisMin.z > otherMax.z || thisMax.z < otherMin.z) false
    else true
  }


}