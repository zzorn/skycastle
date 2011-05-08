package org.jmespike.shape.ships

import simplex3d.math.floatx.Quat4f._

import org.jmespike.conf.Conf
import org.scalaprops.ui.editors.SelectionEditorFactory
import com.jme3.scene.{Node, Spatial}
import simplex3d.math.float.functions._
import simplex3d.math.float._

/**
 * Structural part of a ship, e.g. containing cargo holds, internal systems, or just structural support.
 * May have other components connected to it.  Connected to another component at the base.
 *
 * Generic shape is rectangular.
 */
class Hull extends ShipComponent {

  val front  = p[ShipComponent]('front, new Shell)
  val top    = p[ShipComponent]('top, new Shell)
  val bottom = p[ShipComponent]('bottom, new Shell)
  val left   = p[ShipComponent]('left, new Shell)
  val right  = p[ShipComponent]('right, new Shell)

  val length = p('length, 50)

  val widthScale = p('widthScale, 0.7f)
  val heightScale = p('heightScale, 0.5f)

  val verticalSlant  = p('verticalSlant, 1f)
  val horizontalSlant  = p('horizontalSlant, 0f)


//  def copyVerticalMirror(): Hull = null
//  def copyHorizontalMirror(): Hull = null

  override def createModel(style: ShipConf, base: ComponentBase): Spatial = {

    val cube = base.extractCube(length(), widthScale(), heightScale(), verticalSlant(), horizontalSlant())

    // Create models for each side except the back side, and add them together to one geometry.
    val coreNode = new Node("ShipCore")
    coreNode.attachChild(front()  .createModel(style, cube.makeBase(FrontSide)))
    coreNode.attachChild(top()    .createModel(style, cube.makeBase(TopSide)))
    coreNode.attachChild(bottom() .createModel(style, cube.makeBase(BottomSide)))
    coreNode.attachChild(left()   .createModel(style, cube.makeBase(LeftSide)))
    coreNode.attachChild(right()  .createModel(style, cube.makeBase(RightSide)))
    coreNode

  }

}