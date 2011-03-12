package org.skycastle.util.mesh

import com.jme3.math.{Vector2f, Vector3f}

/**
 * 
 */
case class RoundSegment(center: Vector3f, direction: Vector3f, radius: Float, thetaOffset: Float) extends Segment {


  def centerPos = center

  def pos(theta: Double) = {
    val v = new Vector3f(center.x, center.y, center.z)

    // TODO: 3D Math

    v
  }

  // TODO: Texture projection

  def centerTexturePos = new Vector2f(0,0)

  def texturePos(theta: Double) = new Vector2f(0,0)

}