package org.jmespike.shape.ships

import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.jmespike.utils.MeshBuilder
import simplex3d.math.floatx.functions._

/**
 * Utility class for creating bases
 */
class CubeBase(meshBuilder: MeshBuilder,
               ftl: Int, ftr: Int, fbl: Int, fbr: Int,
               btl: Int, btr: Int, bbl: Int, bbr: Int) {

  def makeBase(side: CubeSide): ComponentBase = {

    side match {
      case FrontSide  => new ComponentBase(meshBuilder, ftr, ftl, fbl, fbr)
      case BackSide   => new ComponentBase(meshBuilder, btl, btr, bbr, bbl)
      case TopSide    => new ComponentBase(meshBuilder, btr, btl, ftl, ftr)
      case BottomSide => new ComponentBase(meshBuilder, fbr, fbl, bbl, bbr)
      case LeftSide   => new ComponentBase(meshBuilder, ftl, btl, bbl, fbl)
      case RightSide  => new ComponentBase(meshBuilder, btr, ftr, fbr, bbr)
    }
  }

  /**
   * Make this cube solid by adding a quad across each side (except the base by default)
   */
  def makeSolid(includeBase: Boolean = false) {
    makeSolidSide(ftr, ftl, fbl, fbr)
    if (includeBase) makeSolidSide(btl, btr, bbr, bbl)
    makeSolidSide(btr, btl, ftl, ftr)
    makeSolidSide(fbr, fbl, bbl, bbr)
    makeSolidSide(ftl, btl, bbl, fbl)
    makeSolidSide(btr, ftr, fbr, bbr)
  }

  private def makeSolidSide(topRight: Int, topLeft: Int, bottomLeft: Int, bottomRight: Int) {
    val tr = meshBuilder.copyVertex(topRight)
    val tl = meshBuilder.copyVertex(topLeft)
    val bl = meshBuilder.copyVertex(bottomLeft)
    val br = meshBuilder.copyVertex(bottomRight)

    meshBuilder.addQuad(tr, tl, bl, br)

    // Add zero sized edges to avoid cracks in the model
    meshBuilder.addQuad(tl, tr, topRight, topLeft)
    meshBuilder.addQuad(tr, br, bottomRight, topRight)
    meshBuilder.addQuad(bl, tl, topLeft, bottomLeft)
    meshBuilder.addQuad(br, bl, bottomLeft, bottomRight)
  }



}



sealed abstract class CubeSide(val dir: Quat4)
// Assume positive is counter clockwise
case object FrontSide extends CubeSide(Quat4.Identity)
case object BackSide extends CubeSide(Quat4.Identity.rotateY(Pi))
case object TopSide extends CubeSide(Quat4.Identity.rotateZ(-Pi/2))
case object BottomSide extends CubeSide(Quat4.Identity.rotateZ(Pi/2))
case object LeftSide extends CubeSide(Quat4.Identity.rotateY(-Pi/2))
case object RightSide extends CubeSide(Quat4.Identity.rotateY(Pi/2))

