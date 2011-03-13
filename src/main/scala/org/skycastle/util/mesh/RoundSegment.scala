package org.skycastle.util.mesh

import com.jme3.math.{Quaternion, Vector2f, Vector3f}
import org.skycastle.util.MathUtils._

/**
 * 
 */
case class RoundSegment(centerX: Float, centerY: Float, centerZ: Float, radius: Float = 1f, textureOffset: Float = 0f, textureWraps: Float = 1f, centerTextureOffset: Float = 0f, direction: Quaternion = new Quaternion(), thetaOffset: Float = 0f) extends Segment {

  val centerPos = new Vector3f(centerX, centerY, centerZ)

  def pos(theta: Float) = {
    val v = new Vector3f(0f,
                         (math.sin(theta + thetaOffset) * radius).toFloat,
                         (math.cos(theta + thetaOffset) * radius).toFloat)
    direction.multLocal(v)
    v.addLocal(centerPos)
    v
  }

  def centerTexturePos = new Vector2f(0.5f, centerTextureOffset)

  def texturePos(theta: Float): Vector2f = {
    new Vector2f((theta * textureWraps + thetaOffset) / Tauf, textureOffset)
  }

}