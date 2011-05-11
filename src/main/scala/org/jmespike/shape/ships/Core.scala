package org.jmespike.shape.ships

import decorations.BoxDeco
import org.jmespike.conf.Conf
import simplex3d.math.float.functions._
import simplex3d.math.float._
import com.jme3.scene.{Node, Spatial}
import org.jmespike.utils.{XorShiftRandom, MeshBuilder}

/**
 * The core of a ship.  The ship construction starts from the core.
 * A ship has one and only one core.
 */
class Core extends Conf {

  val front  = p[ShipComponent]('front, new Shell)
  val back   = p[ShipComponent]('back, new GridShell())
  val top    = p[ShipComponent]('top, new GridShell())
  val bottom = p[ShipComponent]('bottom, new GridShell())
  val left   = p[ShipComponent]('left, new Shell)
  val right  = p[ShipComponent]('right, new Shell)

  val length = p('length, 50f)
  val width = p('width, 30f)
  val height = p('height, 10f)
  val widthScale = p('widthScale, 0f).editor(makeSlider(-1, 1))
  val heightScale = p('heightScale, 0f).editor(makeSlider(-1, 1))

  val verticalSlant  = p('verticalSlant, 0f).editor(makeSlider(-3, 3))
  val horizontalSlant  = p('horizontalSlant, 0f).editor(makeSlider(-3, 3))


  def buildMesh(style: ShipConf, meshBuilder: MeshBuilder, seed: Int) {

    // Ship is aligned in the direction of positive x axis, with positive y up and positive z to left.

    val base = ComponentBase.createBase(meshBuilder, length(), height(), width())
    val cube = base.extractCube(length(), widthScale(), heightScale(), verticalSlant(), horizontalSlant())

    val r = new XorShiftRandom(seed)

    // Build meshes for each side
    front()  .buildMesh(style, cube.makeBase(FrontSide), r.nextInt)
    back()   .buildMesh(style, cube.makeBase(BackSide), r.nextInt)
    top()    .buildMesh(style, cube.makeBase(TopSide), r.nextInt)
    bottom() .buildMesh(style, cube.makeBase(BottomSide), r.nextInt)
    left()   .buildMesh(style, cube.makeBase(LeftSide), r.nextInt)
    right()  .buildMesh(style, cube.makeBase(RightSide), r.nextInt)
  }


}