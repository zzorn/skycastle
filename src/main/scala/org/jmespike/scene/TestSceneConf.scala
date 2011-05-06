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

  val numBalls = p('numBalls, 50)

  val ballAppearance = p('ballAppearance, new AppearanceConf)

}