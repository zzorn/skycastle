package org.jmespike.scene

import org.jmespike.Conf
import org.jmespike.lighting.LightingConf
import org.jmespike.shape.SphereConf
import org.jmespike.appearance.{AppearanceConf, MaterialConf}

/**
 * 
 */
class TestSceneConf extends Conf {

  val lighting = p('lighting, new LightingConf)

  val seed = p('seed, 342)
  val numBalls = p('numBalls, 50)

  val xArea = p('xArea, 100f)
  val yArea = p('yArea, 100f)
  val zArea = p('zArea, 100f)

  val ballAppearance = p('ballAppearance, new AppearanceConf)

}