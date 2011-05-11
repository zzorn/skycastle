package org.jmespike.shape.ships

import com.jme3.scene.{Node, Spatial}
import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.jmespike.utils.{MeshUtils, MeshBuilder}
import simplex3d.math.floatx.functions._

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

  def averageSize = (baseWidth + baseHeight) / 2f


  def extractCube(length: Float = averageSize,
                  scaleWidth: Float = 0,
                  scaleHeight: Float = 0,
                  skewVertically: Float = 0,
                  skewHorizontally: Float = 0): CubeBase = {
    val forward = forwardNormal * length
    val up      = upVector
    val right   = rightVector

    val upLeftVector      = topLeftVertex - bottomLeftVertex
    val upRightVector     = topRightVertex - bottomRightVertex
    val rightTopVector    = topRightVertex - topLeftVertex
    val rightBottomVector = bottomRightVertex - bottomLeftVertex

    val heightLeftAdjust   = upLeftVector      * (max(scaleHeight, -1) * 0.5f)
    val heightRightAdjust  = upRightVector     * (max(scaleHeight, -1) * 0.5f)
    val widthTopAdjust     = rightTopVector    * (max(scaleWidth, -1) * 0.5f)
    val widthBottomAdjust  = rightBottomVector * (max(scaleWidth, -1) * 0.5f)
    val skewAdjust   = up    * skewVertically +
                       right * skewHorizontally

    val frontTopLeft     = topLeftVertex     + forward - widthTopAdjust    + heightLeftAdjust  + skewAdjust
    val frontTopRight    = topRightVertex    + forward + widthTopAdjust    + heightRightAdjust + skewAdjust
    val frontBottomLeft  = bottomLeftVertex  + forward - widthBottomAdjust - heightLeftAdjust  + skewAdjust
    val frontBottomRight = bottomRightVertex + forward + widthBottomAdjust - heightRightAdjust + skewAdjust

    // Generate vertexes for the new corners
    val ftl = meshBuilder.addVertex(frontTopLeft)
    val ftr = meshBuilder.addVertex(frontTopRight)
    val fbl = meshBuilder.addVertex(frontBottomLeft)
    val fbr = meshBuilder.addVertex(frontBottomRight)


    new CubeBase(meshBuilder,
                 ftl, ftr, fbl, fbr,
                 topLeft, topRight, bottomLeft, bottomRight)
  }


  /**
   * Create a rectangular border with a rectangular hole
   */
  def extractBorder(marginFraction: Float = 0.1f): ComponentBase = {
    val size = clamp(marginFraction, 0f, 1f)

    // TODO: Mixin texel, color, and normal too
    val tr = meshBuilder.addVertex(mix(topRightVertex, baseCenter, size))
    val tl = meshBuilder.addVertex(mix(topLeftVertex, baseCenter, size))
    val bl = meshBuilder.addVertex(mix(bottomLeftVertex, baseCenter, size))
    val br = meshBuilder.addVertex(mix(bottomRightVertex, baseCenter, size))

    meshBuilder.addQuad(tl, tr, topRight, topLeft)
    meshBuilder.addQuad(tr, br, bottomRight, topRight)
    meshBuilder.addQuad(bl, tl, topLeft, bottomLeft)
    meshBuilder.addQuad(br, bl, bottomLeft, bottomRight)

    new ComponentBase(meshBuilder, tr, tl, bl, br)
  }

  /**
   * Extract several new component bases by dividing this component base in a regular grid.
   */
  def extractGrid(sizeLeftRight: Int = 2,
                  sizeTopBottom: Int = 2): List[ComponentBase] = {

    if (sizeLeftRight <= 0 || sizeTopBottom <= 0) Nil
    else {

      // Generate vertex grid
      val vertexesWide = sizeLeftRight + 1
      val vertexesHigh = sizeTopBottom + 1

      val bottomLeftTexel   = meshBuilder.texel(bottomLeft)
      val topLeftTexel      = meshBuilder.texel(topLeft)
      val bottomRightTexel  = meshBuilder.texel(bottomRight)
      val topRightTexel     = meshBuilder.texel(topRight)

      val bottomLeftColor   = meshBuilder.color(bottomLeft)
      val topLeftColor      = meshBuilder.color(topLeft)
      val bottomRightColor  = meshBuilder.color(bottomRight)
      val topRightColor     = meshBuilder.color(topRight)

      val bottomLeftNormal  = meshBuilder.normal(bottomLeft)
      val topLeftNormal     = meshBuilder.normal(topLeft)
      val bottomRightNormal = meshBuilder.normal(bottomRight)
      val topRightNormal    = meshBuilder.normal(topRight)

      val indexes = new Array[Int](vertexesWide * vertexesHigh)

      var i = 0
      var v = 0
      while(v <= sizeTopBottom) {

        val rv = v * 1f / (sizeTopBottom)

        val leftPos  = mix(topLeftVertex,  bottomLeftVertex,  rv)
        val rightPos = mix(topRightVertex, bottomRightVertex, rv)

        val leftTex  = mix(topLeftTexel,   bottomLeftTexel,   rv)
        val rightTex = mix(topRightTexel,  bottomRightTexel,  rv)

        val leftCol  = mix(topLeftColor,   bottomLeftColor,   rv)
        val rightCol = mix(topRightColor,  bottomRightColor,  rv)

        val leftNor  = mix(topLeftNormal,  bottomLeftNormal,  rv)
        val rightNor = mix(topRightNormal, bottomRightNormal, rv)

        var u = 0
        while(u <= sizeLeftRight) {

          // Use existing corner vertexes for the corners
          indexes(i) =
            if (v == 0 && u == 0) topLeft
            else if (v == 0 && u == sizeLeftRight) topRight
            else if (v == sizeTopBottom && u == 0) bottomLeft
            else if (v == sizeTopBottom && u == sizeLeftRight) bottomRight
            else {
              // Create new vertexes for inner corners
              val ru = u * 1f / (sizeLeftRight)
              val pos = mix(leftPos, rightPos, ru)
              val tex = mix(leftTex, rightTex, ru)
              val col = mix(leftCol, rightCol, ru)
              val nor = mix(leftNor, rightNor, ru)

              meshBuilder.addVertex(pos, tex, col, nor)
            }

          i += 1
          u += 1
        }

        v += 1
      }

      // Generate component bases using the vertex grid
      // Generate them in inverse order as ::= adds to front and not end
      var gridBases: List[ComponentBase] = Nil
      var cv = sizeTopBottom - 1
      while(cv >= 0) {

        var cu = sizeLeftRight - 1
        while(cu >= 0) {

          val lookupIndex = cu + cv * vertexesWide
          val gridTopLeft     = indexes(lookupIndex)
          val gridTopRight    = indexes(lookupIndex + 1)
          val gridBottomLeft  = indexes(lookupIndex + vertexesWide)
          val gridBottomRight = indexes(lookupIndex + vertexesWide + 1)

          gridBases ::= new ComponentBase(meshBuilder, gridTopRight, gridTopLeft, gridBottomLeft, gridBottomRight)

          cu -= 1
        }

        cv -= 1
      }

      gridBases
    }
  }

  /**
   * Make this base solid by adding a quad across it
   */
  def makeSolid() {
    meshBuilder.addQuad(bottomLeft, bottomRight, topRight, topLeft)
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

