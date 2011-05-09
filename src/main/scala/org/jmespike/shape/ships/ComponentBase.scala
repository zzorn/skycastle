package org.jmespike.shape.ships

import com.jme3.scene.{Node, Spatial}
import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.jmespike.utils.{MeshUtils, MeshBuilder}

/**
 * The base for a ship component, that it is built on.
 * A four-cornered polygon.
 */
// TODO: Also tell what the angles are to adjacent bases? - allows continuous surfaces, special treatment of edges, etc.
case class ComponentBase(meshBuilder: MeshBuilder,
                         topRight: Int,
                         topLeft: Int,
                         bottomLeft: Int,
                         bottomRight: Int) {

  val topRightVertex    : ConstVec3 = meshBuilder.vertex(topRight)
  val topLeftVertex     : ConstVec3 = meshBuilder.vertex(topLeft)
  val bottomLeftVertex  : ConstVec3 = meshBuilder.vertex(bottomLeft)
  val bottomRightVertex : ConstVec3 = meshBuilder.vertex(bottomRight)

  val upVector    = (0.5f * (topLeftVertex + topRightVertex))     - (0.5f * (bottomLeftVertex + bottomRightVertex))
  val rightVector = (0.5f * (topRightVertex + bottomRightVertex)) - (0.5f * (topLeftVertex + bottomLeftVertex))

  val baseCenter : ConstVec3 = 0.25f * (topRightVertex + topLeftVertex + bottomLeftVertex + bottomRightVertex)

  def forwardNormal: Vec3 = {
    // Get normal of the base surface
    val n1 = MeshUtils.triangleNormal(topRightVertex, topLeftVertex, bottomLeftVertex)
    val n2 = MeshUtils.triangleNormal(bottomLeftVertex, bottomRightVertex, topRightVertex)
    normalize(n1 + n2)
  }

  def baseWidth  = length(rightVector)
  def baseHeight = length(upVector)

  
  def extractCube(length: Float, scaleWidth: Float, scaleHeight: Float, skewVertically: Float, skewHorizontally: Float): CubeBase = {
    val forward = forwardNormal * length
    val up      = upVector
    val right   = rightVector

    val widthAdjust  = right * (max(scaleWidth, -1) * 0.5f)
    val heightAdjust = up    * (max(scaleHeight, -1) * 0.5f)
    val skewAdjust   = up    * skewVertically +
                       right * skewHorizontally

    val frontTopLeft     = topLeftVertex     + forward - widthAdjust + heightAdjust + skewAdjust
    val frontTopRight    = topRightVertex    + forward + widthAdjust + heightAdjust + skewAdjust
    val frontBottomLeft  = bottomLeftVertex  + forward - widthAdjust - heightAdjust + skewAdjust
    val frontBottomRight = bottomRightVertex + forward + widthAdjust - heightAdjust + skewAdjust

    // Generate vertexes for the new corners
    val ftl = meshBuilder.addVertex(frontTopLeft)
    val ftr = meshBuilder.addVertex(frontTopRight)
    val fbl = meshBuilder.addVertex(frontBottomLeft)
    val fbr = meshBuilder.addVertex(frontBottomRight)


    new CubeBase(meshBuilder,
                 ftl, ftr, fbl, fbr,
                 topLeft, topRight, bottomLeft, bottomRight)
  }
}

object ComponentBase {

  def createBase(meshBuilder: MeshBuilder,
                 xLength:     Float       = 1f,
                 yHeight:     Float       = 1f,
                 zWidth:      Float       = 1f,
                 center:      inVec3      = Vec3.Zero): ComponentBase = {

    val xSize      = Vec3(xLength / 2f, 0, 0)
    val ySizeBack  = Vec3(0, yHeight / 2f, 0)
    val zSizeBack  = Vec3(0, 0, zWidth / 2f)

    val backTopLeft      = center - xSize + ySizeBack  - zSizeBack
    val backTopRight     = center - xSize + ySizeBack  + zSizeBack
    val backBottomLeft   = center - xSize - ySizeBack  - zSizeBack
    val backBottomRight  = center - xSize - ySizeBack  + zSizeBack

    val btl = meshBuilder.addVertex(backTopLeft)
    val btr = meshBuilder.addVertex(backTopRight)
    val bbl = meshBuilder.addVertex(backBottomLeft)
    val bbr = meshBuilder.addVertex(backBottomRight)

    new ComponentBase(meshBuilder, btr, btl, bbl, bbr)
  }

}

