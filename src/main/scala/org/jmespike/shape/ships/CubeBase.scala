package org.jmespike.shape.ships

import simplex3d.math.float.functions._
import simplex3d.math.float._

/**
 * Utility class for creating bases
 */
class CubeBase(frontDir: Quat4,
               frontTopLeft: Vec3,    frontTopRight: Vec3,
               frontBottomLeft: Vec3, frontBottomRight: Vec3,
               backTopLeft: Vec3,     backTopRight: Vec3,
               backBottomLeft: Vec3,  backBottomRight: Vec3) {

  def makeBase(side: CubeSide): ComponentBase = {

    val d = side.dir.rotate(frontDir)

    side match {
      case FrontSide  => new ComponentBase(frontTopRight, frontTopLeft, frontBottomLeft, frontBottomRight, d)
      case BackSide   => new ComponentBase(backTopLeft, backTopRight, backBottomRight, backBottomLeft, d)
      case TopSide    => new ComponentBase(backTopRight, backTopLeft, frontTopLeft, frontTopRight, d)
      case BottomSide => new ComponentBase(frontBottomRight, frontBottomLeft, backBottomLeft, backBottomRight, d)
      case LeftSide   => new ComponentBase(frontTopLeft, backTopLeft, backBottomLeft, frontBottomLeft, d)
      case RightSide  => new ComponentBase(backTopRight, frontTopRight, frontBottomRight, backBottomRight, d)
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




