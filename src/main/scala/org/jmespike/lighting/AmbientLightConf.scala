package org.jmespike.lighting

import com.jme3.math.{Vector3f, ColorRGBA}
import com.jme3.light.{AmbientLight, DirectionalLight}

import util.Random
import org.jmespike.conf.Conf
import org.jmespike.conf.ColorConf

import org.jmespike.utils.VectorConversions._

/**
 *
 */
class AmbientLightConf extends Conf {

  val color = p('color, new ColorConf())
  val intensity = p('intensity, 1f).editor(makeSlider(-5, 5))

  def configure(light: AmbientLight) {
    light.setColor(color().getColor * intensity())
  }
}