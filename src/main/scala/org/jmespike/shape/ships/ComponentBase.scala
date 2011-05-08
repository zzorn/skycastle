package org.jmespike.shape.ships

import com.jme3.scene.{Node, Spatial}
import simplex3d.math.float.functions._
import simplex3d.math.float._

/**
 * The base for a ship component, that it is built on.
 * A four-cornered polygon.
 */
// TODO: Also tell what the angles are to adjacent bases? - allows continuous surfaces, special treatment of edges, etc.
case class ComponentBase(topRight: inVec3,
                         topLeft: inVec3,
                         bottomLeft: inVec3,
                         bottomRight: inVec3,
                         orientation: inQuat4) {

  val baseCenter: Vec3 = 0.25f * (topRight + topLeft + bottomLeft + bottomRight)

  def baseWidth = distance((0.5f * (topLeft + bottomLeft)), (0.5f * (topRight + bottomRight)))
  def baseHeight = distance((0.5f * (topLeft + topRight)), (0.5f * (bottomLeft + bottomRight)))

  
  def extractCube(length: Float, scaleWidth: Float, scaleHeight: Float, skewVertically: Float, skewHorizontally: Float): CubeBase = {
    val forward = rotateVector(Vec3(length, 0, 0), orientation)
    val up      = rotateVector(Vec3(0, baseHeight, 0), orientation)
    val left    = rotateVector(Vec3(0, 0, baseWidth), orientation)

    val widthAdjust  = left * scaleWidth * 0.5f
    val heightAdjust = up   * scaleHeight * 0.5f
    val skewAdjust   = up   * skewVertically + left * skewHorizontally

    val frontTopLeft     = topLeft     + forward - widthAdjust - heightAdjust + skewAdjust
    val frontTopRight    = topRight    + forward + widthAdjust - heightAdjust + skewAdjust
    val frontBottomLeft  = bottomLeft  + forward - widthAdjust + heightAdjust + skewAdjust
    val frontBottomRight = bottomRight + forward + widthAdjust + heightAdjust + skewAdjust

    new CubeBase(orientation,
                 frontTopLeft, frontTopRight, frontBottomLeft, frontBottomRight,
                 topLeft, topRight, bottomLeft, bottomRight)
  }
}

object ComponentBase {
  def createBase(xLength: Float = 1f,
                 yHeight: Float = 1f,
                 zWidth: Float = 1f,
                 center: inVec3 = Vec3.Zero,
                 orientation: inQuat4 = Quat4.Identity): ComponentBase = {
    val xSize      = Vec3(xLength / 2f, 0, 0)
    val ySizeBack  = Vec3(0, yHeight / 2f, 0)
    val zSizeBack  = Vec3(0, 0, zWidth / 2f)

    val backTopLeft      = center - xSize + ySizeBack  + zSizeBack
    val backTopRight     = center - xSize + ySizeBack  - zSizeBack
    val backBottomLeft   = center - xSize - ySizeBack  + zSizeBack
    val backBottomRight  = center - xSize - ySizeBack  - zSizeBack

    new ComponentBase(backTopRight, backTopLeft, backBottomLeft, backBottomRight, orientation)
  }

}

