package org.jmespike.shape.ships

import com.jme3.scene.{Node, Spatial}
import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.jmespike.utils.MeshBuilder

/**
 * The base for a ship component, that it is built on.
 * A four-cornered polygon.
 */
// TODO: Also tell what the angles are to adjacent bases? - allows continuous surfaces, special treatment of edges, etc.
case class ComponentBase(meshBuilder: MeshBuilder,
                         orientation: inQuat4,
                         topRight: Int,
                         topLeft: Int,
                         bottomLeft: Int,
                         bottomRight: Int) {

  private val topRightVertex    = meshBuilder.vertex(topRight)
  private val topLeftVertex     = meshBuilder.vertex(topLeft)
  private val bottomLeftVertex  = meshBuilder.vertex(bottomLeft)
  private val bottomRightVertex = meshBuilder.vertex(bottomRight)

  val baseCenter : ConstVec3 = 0.25f * (topRightVertex + topLeftVertex + bottomLeftVertex + bottomRightVertex)

  def baseWidth  = distance((0.5f * (topLeftVertex + bottomLeftVertex)), (0.5f * (topRightVertex + bottomRightVertex)))
  def baseHeight = distance((0.5f * (topLeftVertex + topRightVertex)), (0.5f * (bottomLeftVertex + bottomRightVertex)))

  
  def extractCube(length: Float, scaleWidth: Float, scaleHeight: Float, skewVertically: Float, skewHorizontally: Float): CubeBase = {
    val forward = rotateVector(Vec3(length, 0, 0), orientation)
    val up      = rotateVector(Vec3(0, baseHeight, 0), orientation)
    val left    = rotateVector(Vec3(0, 0, baseWidth), orientation)

    val widthAdjust  = left * scaleWidth * 0.5f
    val heightAdjust = up   * scaleHeight * 0.5f
    val skewAdjust   = up   * skewVertically + left * skewHorizontally

    val frontTopLeft     = topLeftVertex     + forward - widthAdjust - heightAdjust + skewAdjust
    val frontTopRight    = topRightVertex    + forward + widthAdjust - heightAdjust + skewAdjust
    val frontBottomLeft  = bottomLeftVertex  + forward - widthAdjust + heightAdjust + skewAdjust
    val frontBottomRight = bottomRightVertex + forward + widthAdjust + heightAdjust + skewAdjust

    // Generate vertexes for the new corners
    val ftl = meshBuilder.addVertex(frontTopLeft)
    val ftr = meshBuilder.addVertex(frontTopRight)
    val fbl = meshBuilder.addVertex(frontBottomLeft)
    val fbr = meshBuilder.addVertex(frontBottomRight)


    new CubeBase(meshBuilder,
                 orientation,
                 ftl, ftr, fbl, fbr,
                 topLeft, topRight, bottomLeft, bottomRight)
  }
}

object ComponentBase {

  def createBase(meshBuilder: MeshBuilder,
                 xLength:     Float       = 1f,
                 yHeight:     Float       = 1f,
                 zWidth:      Float       = 1f,
                 center:      inVec3      = Vec3.Zero,
                 orientation: inQuat4     = Quat4.Identity): ComponentBase = {

    val xSize      = Vec3(xLength / 2f, 0, 0)
    val ySizeBack  = Vec3(0, yHeight / 2f, 0)
    val zSizeBack  = Vec3(0, 0, zWidth / 2f)

    val backTopLeft      = center - xSize + ySizeBack  + zSizeBack
    val backTopRight     = center - xSize + ySizeBack  - zSizeBack
    val backBottomLeft   = center - xSize - ySizeBack  + zSizeBack
    val backBottomRight  = center - xSize - ySizeBack  - zSizeBack

    val btl = meshBuilder.addVertex(backTopLeft)
    val btr = meshBuilder.addVertex(backTopRight)
    val bbl = meshBuilder.addVertex(backBottomLeft)
    val bbr = meshBuilder.addVertex(backBottomRight)

    new ComponentBase(meshBuilder, orientation, btr, btl, bbl, bbr)
  }

}

