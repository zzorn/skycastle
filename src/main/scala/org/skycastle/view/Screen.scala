package org.skycastle.view

import org.sgine.property.adjust.{EasingNumericAdjuster, LinearNumericAdjuster}
import org.sgine.ui.ImageCube
import org.sgine.effect.{CompositeEffect, PropertyChangeEffect}
import org.sgine.render.scene.RenderableScene
import org.sgine.core.{Resource, Color}
import org.sgine.render._
import org.sgine.render.{Image => RenderImage}
import org.sgine.math.mutable.Matrix4
import primitive.{Primitive, Disk, Cuboid}
import org.sgine.scene.{Node, GeneralNodeContainer}
import org.sgine.easing.{Cubic, Sine, Elastic}
import shape.sgine.{EasingColorAdjuster, Box}
import scala.math._

/**
 * 
 */
class Screen extends StandardDisplay {

  private var m: Matrix4 = null

  private var box: Box = null

  def setup() {

    box = new Box()
    scene +=  box

    Resource.addPath("")
    box.texture := RenderImage(TextureManager(Resource("textures/test_crate.png")))
    box.color := Color.Maroon

    box.width.adjuster = new EasingNumericAdjuster(Cubic.easeInOut, 1)
    box.height.adjuster = new EasingNumericAdjuster(Cubic.easeInOut, 1)
    box.depth.adjuster = new EasingNumericAdjuster(Cubic.easeInOut, 1)

    box.rotation.x.adjuster = new EasingNumericAdjuster(Cubic.easeInOut, 1)
    box.rotation.y.adjuster = new EasingNumericAdjuster(Cubic.easeInOut, 1)
    box.rotation.z.adjuster = new EasingNumericAdjuster(Cubic.easeInOut, 1)

    box.color. adjuster = new EasingColorAdjuster(Cubic.easeInOut,2)

  }

  // TODO: Move to some main game class
  def run() {
    start()
    //renderer.fullscreen := true
    while(true) {
      Thread.sleep(1000)
      update()
    }
  }

  def update() {
    box.width := random * 500
    box.height := random * 500
    box.depth := random * 500

    box.rotation.x := random * 2 * Pi
    box.rotation.y := random * 2 * Pi
    box.rotation.z := random * 2 * Pi

    box.color := Color(random, random, random, 1)
  }

  def makeTree() : Node = {
    new Box(1, 100, 1)
  }


}