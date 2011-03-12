package org.skycastle.util.mesh

import com.jme3.math.{Vector2f, Vector3f}

/**
 * Shape for one segment separator on a lathe.
 *
 * Theta is the angle around the trunk in radians.
 */
trait Segment {

  def pos(theta: Double): Vector3f
  def texturePos(theta: Double): Vector2f

  def centerPos: Vector3f
  def centerTexturePos: Vector2f

}