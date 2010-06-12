package org.skycastle.view

import org.sgine.property.adjust.{EasingNumericAdjuster, LinearNumericAdjuster}
import org.sgine.ui.ImageCube
import org.sgine.scene.GeneralNodeContainer
import org.sgine.effect.{CompositeEffect, PropertyChangeEffect}
import org.sgine.render.scene.RenderableScene
import org.sgine.core.{Resource, Color}
import org.sgine.easing.Elastic
import org.sgine.render._
import org.sgine.math.mutable.Matrix4
import primitive.{Disk, Cuboid}

/**
 * 
 */
class Screen {

  def start() {

    makeScene
  }


  def makeScene() {
    val r = Renderer.createFrame(1024, 768, "Test Cuboid", 4, 8, 4, 4)
      //r.verticalSync := false

/*
		val t = TextureUtil(ImageIO.read(getClass.getClassLoader.getResource("resource/puppies.jpg")))
*/

      val m = Matrix4().translate(z = -1000.0).scaleAll(0.04)
/*
		val i = Image(t)
*/
      val cuboid = Cuboid(300.0, 100.0, 200.0, Color.White, null)
      val fps = FPS()

      r.renderable := RenderList(MatrixState(m), cuboid, fps)


      while(true) {
        Thread.sleep(5)
        m.rotate(0.005, 0.005, 0.0)

        //Thread.sleep(10)
      }

  }
}