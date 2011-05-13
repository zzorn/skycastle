package org.jmespike.shape.ships

import decorations.BoxDeco
import simplex3d.math.floatx.Quat4f._

import org.jmespike.conf.Conf
import org.scalaprops.ui.editors.SelectionEditorFactory
import com.jme3.scene.{Node, Spatial}
import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.jmespike.utils.XorShiftRandom

/**
 * Structural part of a ship, e.g. containing cargo holds, internal systems, or just structural support.
 * May have other components connected to it.  Connected to another component at the base.
 *
 * Generic shape is rectangular.
 */
class Chassis extends ShipComponent {

  val front  = p[ShipComponent]('front,  new GridShell)
  val top    = p[ShipComponent]('top,    new GridShell)
  val bottom = p[ShipComponent]('bottom, new GridShell)
  val left   = p[ShipComponent]('left,   new GridShell)
  val right  = p[ShipComponent]('right,  new GridShell)

  val length = p('length, 50)

  val widthScale = p('widthScale, 0f).editor(makeSlider(-1, 1))
  val heightScale = p('heightScale, 0f).editor(makeSlider(-1, 1))

  val verticalSlant  = p('verticalSlant, 0f).editor(makeSlider(-3, 3))
  val horizontalSlant  = p('horizontalSlant, 0f).editor(makeSlider(-3, 3))



  override def buildMesh(style: ShipShapeConf, base: ComponentBase, seed: Int) {

    val cube = base.extractCube(length(), widthScale(), heightScale(), verticalSlant(), horizontalSlant())

    val r = new XorShiftRandom(seed)

    // Create mesh for each side except the back side
    front()  .buildMesh(style, cube.makeBase(FrontSide), r.nextInt)
    top()    .buildMesh(style, cube.makeBase(TopSide), r.nextInt)
    bottom() .buildMesh(style, cube.makeBase(BottomSide), r.nextInt)
    left()   .buildMesh(style, cube.makeBase(LeftSide), r.nextInt)
    right()  .buildMesh(style, cube.makeBase(RightSide), r.nextInt)
  }

}