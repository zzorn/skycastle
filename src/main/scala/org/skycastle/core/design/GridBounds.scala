package org.skycastle.core.design

import grid.{Vec3i, GridSize}
import com.jme3.math.Vector3f
import math._
import com.jme3.bounding.BoundingBox


/**
 * 
 */
case class GridBounds(gridSize: GridSize, a: Vec3i, b: Vec3i) {

  def min: Vector3f = {
    val s = gridSize.gridSizeMeters
    new Vector3f(s * (a.x min b.x),
                 s * (a.y min b.y),
                 s * (a.z min b.z))
  }

  def max: Vector3f = {
    val s = gridSize.gridSizeMeters
    new Vector3f(s * (a.x max b.x),
                 s * (a.y max b.y),
                 s * (a.z max b.z))
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