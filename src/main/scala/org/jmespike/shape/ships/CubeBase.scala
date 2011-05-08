package org.jmespike.shape.ships

import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.jmespike.utils.MeshBuilder

/**
 * Utility class for creating bases
 */
class CubeBase(meshBuilder: MeshBuilder,
               frontDir: inQuat4,
               ftl: Int, ftr: Int, fbl: Int, fbr: Int,
               btl: Int, btr: Int, bbl: Int, bbr: Int) {

  def makeBase(side: CubeSide): ComponentBase = {

    val d = side.dir.rotate(frontDir)

    side match {
      case FrontSide  => new ComponentBase(meshBuilder, d, ftr, ftl, fbl, fbr)
      case BackSide   => new ComponentBase(meshBuilder, d, btl, btr, bbr, bbl)
      case TopSide    => new ComponentBase(meshBuilder, d, btr, btl, ftl, ftr)
      case BottomSide => new ComponentBase(meshBuilder, d, fbr, fbl, bbl, bbr)
      case LeftSide   => new ComponentBase(meshBuilder, d, ftl, btl, bbl, fbl)
      case RightSide  => new ComponentBase(meshBuilder, d, btr, ftr, fbr, bbr)
    }
  }
}




sealed abstract class CubeSide(val dir: Quat4)
// Assume positive is counter clockwise
case object FrontSide extends CubeSide(Quat4.Identity)
case object BackSide extends CubeSide(Quat4.Identity.rotateY(Pi))
case object TopSide extends CubeSide(Quat4.Identity.rotateZ(-Pi/2))
case object BottomSide extends CubeSide(Quat4.Identity.rotateZ(Pi/2))
case object LeftSide extends CubeSide(Quat4.Identity.rotateY(Pi/2))
case object RightSide extends CubeSide(Quat4.Identity.rotateY(-Pi/2))




