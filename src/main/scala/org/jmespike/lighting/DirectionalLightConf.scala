package org.jmespike.lighting

import com.jme3.light.DirectionalLight
import com.jme3.math.{Vector3f, ColorRGBA}
import util.Random
import org.jmespike.conf.Conf
import org.jmespike.conf.ColorConf
import org.jmespike.utils.VectorConversions._

/**
 *
 */
class DirectionalLightConf extends Conf {

  val color = p('color, new ColorConf)
  val intensity = p('intensity, 1f).editor(makeSlider(0, 4))

  val x = p('x, 0.3f).editor(makeSlider(-1, 1))
  val y = p('y, 0.5f).editor(makeSlider(-1, 1))
  val z = p('z, 0.1f).editor(makeSlider(-1, 1))

  def configure(light: DirectionalLight) {
    light.setColor(color().getColor * intensity())
    light.setDirection(new Vector3f(x(), y(), z()).normalizeLocal())
  }
}