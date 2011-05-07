package org.jmespike.lighting

import org.jmespike.conf.Conf
import org.jmespike.conf.ColorConf
import com.jme3.light.{DirectionalLight, AmbientLight}
import com.jme3.app.SimpleApplication._
import com.jme3.scene.Spatial

/**
 * The static lighting of a scene.
 */
class LightingConf extends Conf {

  val skyColor = p('skyColor, new ColorConf())

  val ambient = p('ambient, new AmbientLightConf)

  // TODO: Add list support to scalaprops
  val directional1 = p('directional1, new DirectionalLightConf)
  val directional2 = p('directional2, new DirectionalLightConf)
  val directional3 = p('directional3, new DirectionalLightConf)


}