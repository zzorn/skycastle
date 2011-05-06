package org.jmespike.scene

import com.jme3.scene.{Spatial, Node}
import org.jmespike.lighting.Lighting
import org.jmespike.utils.XorShiftRandom
import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.jmespike.utils.VectorConversions._

/**
 * 
 */
// TODO: This could be moved to test scene conf, if it wasn't for extending scene and scene keeping track of the node -> make scene general purpose, that just takes a scene factory
class TestScene(conf: TestSceneConf) extends Scene {

  var lighting: Lighting = null

  def createRoot: Spatial = {
    val root = new Node

    // Add lights
    lighting = new Lighting(root, conf.lighting())

    // Add some content
    val rng = new XorShiftRandom(conf.seed())
    val num = conf.numBalls()
    for (i <- 0 until num) {
      val pos = Vec3((rng.nextGaussian * conf.xArea()).toFloat,
                     (rng.nextGaussian * conf.yArea()).toFloat,
                     (rng.nextGaussian * conf.zArea()).toFloat)

      val ball = conf.ballAppearance().createSpatial(rng)
      ball.setLocalTranslation(pos)
      root.attachChild(ball)
    }

    root
  }

}