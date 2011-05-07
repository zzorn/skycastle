package org.jmespike.scene

import com.jme3.scene.{Spatial, Node}
import org.jmespike.utils.XorShiftRandom
import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.jmespike.utils.VectorConversions._
import org.jmespike.lighting.{LightingConf, Lighting}
import org.jmespike.appearance.AppearanceConf

/**
 * 
 */
class TestScene() extends SceneFactory {

  val lighting = p('lighting, new LightingConf)

  val seed = p('seed, 342)
  val numBalls = p('numBalls, 50)

  val xArea = p('xArea, 100f)
  val yArea = p('yArea, 100f)
  val zArea = p('zArea, 100f)

  val ballAppearance = p('ballAppearance, new AppearanceConf)

  def createScene: Spatial = {
    val root = new Node

    // Add lights
    val lights: Lighting = new Lighting(root, lighting())

    // Add some content
    val rng = new XorShiftRandom(seed())
    val num = numBalls()
    for (i <- 0 until num) {
      val pos = Vec3((rng.nextGaussian * xArea()).toFloat,
                     (rng.nextGaussian * yArea()).toFloat,
                     (rng.nextGaussian * zArea()).toFloat)

      val ball = ballAppearance().createSpatial(rng)
      ball.setLocalTranslation(pos)
      root.attachChild(ball)
    }

    root
  }

}