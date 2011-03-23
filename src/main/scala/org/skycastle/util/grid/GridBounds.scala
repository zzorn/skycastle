package org.skycastle.util.grid

import com.jme3.math.Vector3f
import math._
import org.skycastle.util.Vec3i


/**
 * 
 */
case class GridBounds(gridSize: GridSize, a: Vec3i, b: Vec3i) {

  def min: Vector3f = {
    val s = gridSize.gridSizeMeters
    new Vector3f(s * (a.x min b.x).toFloat,
                 s * (a.y min b.y).toFloat,
                 s * (a.z min b.z).toFloat)
  }

  def max: Vector3f = {
    val s = gridSize.gridSizeMeters
    new Vector3f(s * (a.x max b.x).toFloat,
                 s * (a.y max b.y).toFloat,
                 s * (a.z max b.z).toFloat)
  }

  def overlaps(gridPos: GridPos): Boolean = {
    return overlaps(gridPos.bounds)
  }

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