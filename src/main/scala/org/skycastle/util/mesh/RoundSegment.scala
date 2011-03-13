package org.skycastle.util.mesh

import com.jme3.math.{Quaternion, Vector2f, Vector3f}
import org.skycastle.util.MathUtils._

/**
 * 
 */
case class RoundSegment(center: Vector3f, radius: Float = 1f, textureOffset: Float = 0f, centerTextureOffset: Float = 0f, direction: Quaternion = new Quaternion(), thetaOffset: Float = 0f) extends Segment {

  def centerPos = center

  def pos(theta: Double) = {
    val v = new Vector3f(0f,
                         (math.sin(theta + thetaOffset) * radius).toFloat,
                         (math.cos(theta + thetaOffset) * radius).toFloat)
    direction.multLocal(v)
    v.addLocal(center)
    v
  }

  def centerTexturePos = new Vector2f(0.5f, centerTextureOffset)

  def texturePos(theta: Double): Vector2f = {
    new Vector2f(((theta + thetaOffset) / Tau).toFloat, textureOffset)
  }

}