package org.jmespike.shape.ships

import org.jmespike.conf.Conf
import simplex3d.math.float.functions._
import simplex3d.math.float._
import com.jme3.scene.{Node, Spatial}

/**
 * The core of a ship.  The ship construction starts from the core.
 * A ship has one and only one core.
 */
class Core extends Conf {

  val front  = p[ShipComponent]('front, new Shell())
  val back   = p[ShipComponent]('back, new Shell())
  val top    = p[ShipComponent]('top, new Shell())
  val bottom = p[ShipComponent]('bottom, new Shell())
  val left   = p[ShipComponent]('left, new Shell())
  val right  = p[ShipComponent]('right, new Shell())

  val length = p('length, 50f)
  val width = p('width, 30f)
  val height = p('height, 10f)
  val widthScale = p('widthScale, 0.7f)
  val heightScale = p('heightScale, 0.5f)

  val verticalSlant  = p('verticalSlant, 1f)
  val horizontalSlant  = p('horizontalSlant, 0f)


  def createModel(style: ShipConf): Spatial = {

    // Ship is aligned in the direction of positive x axis, with positive y up and positive z to left.

    val base = ComponentBase.createBase(length(), height(), width())
    val cube = base.extractCube(length(), widthScale(), heightScale(), verticalSlant(), horizontalSlant())

    // Create models for each side, and add them together to one geometry.
    val coreNode = new Node("ShipCore")
    coreNode.attachChild(front()  .createModel(style, cube.makeBase(FrontSide)))
    coreNode.attachChild(back()   .createModel(style, cube.makeBase(BackSide)))
    coreNode.attachChild(top()    .createModel(style, cube.makeBase(TopSide)))
    coreNode.attachChild(bottom() .createModel(style, cube.makeBase(BottomSide)))
    coreNode.attachChild(left()   .createModel(style, cube.makeBase(LeftSide)))
    coreNode.attachChild(right()  .createModel(style, cube.makeBase(RightSide)))
    coreNode
  }


}