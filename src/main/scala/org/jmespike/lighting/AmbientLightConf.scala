package org.jmespike.lighting

import com.jme3.math.{Vector3f, ColorRGBA}
import com.jme3.light.{AmbientLight, DirectionalLight}

import util.Random
import org.jmespike.Conf
import org.jmespike.conf.ColorConf

import org.jmespike.utils.VectorConversions._

/**
 *
 */
class AmbientLightConf extends Conf {

  val color = p('color, new ColorConf())
  val intensity = p('intensity, 1f).editor(makeSlider(0, 4))

  def configure(light: AmbientLight, random: Random) {
    light.setColor(color().createColor(random) * intensity())
    light.setColor(new ColorRGBA(0.3f, 0.2f, 0.1f, 1f).multLocal(3f))
  }
}