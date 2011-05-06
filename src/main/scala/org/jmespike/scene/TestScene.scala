package org.jmespike.scene

import com.jme3.scene.{Spatial, Node}
import org.jmespike.lighting.Lighting
import org.jmespike.utils.XorShiftRandom

/**
 * 
 */
class TestScene(conf: TestSceneConf) extends Scene {

  var lighting: Lighting = null

  def createRoot: Spatial = {
    val root = new Node

    // Add lights
    lighting = new Lighting(root, conf.lighting())

    // Add some content
    val rng = new XorShiftRandom()
    val num = conf.numBalls()
    for (i <- 0 until num) {
      root.attachChild(conf.ballAppearance().createSpatial(rng))
    }

    root
  }

}