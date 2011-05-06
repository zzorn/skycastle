package org.jmespike.conf

import org.scalaprops.Bean
import util.Random
import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.scalaprops.ui.editors.{ColoredSliderBackgroundPainter, SliderFactory}
import java.awt.Color
import org.jmespike.Conf
import org.jmespike.utils.RandomUtils

/**
 *
 */
class ColorConf() extends Conf {

  private val baseEditor = makeSlider(hue=0.1, lum = 0.2)
  private val spreadEditor = makeSlider(end = 0.5, hue=0.7, lum = 0.6)

  // TODO: Separate UI name for beans, beanName is used when loading
  //beanName = Symbol(_name)

  val hue = p('hue, 0f).editor(baseEditor)
  val sat = p('sat, 0.5f).editor(baseEditor)
  val lum = p('lum, 0.5f).editor(baseEditor)

  val hueVariation = p('hueVariation, 0.1f).editor(spreadEditor)
  val satVariation = p('satVariation, 0.1f).editor(spreadEditor)
  val lumVariation = p('lumVariation, 0.1f).editor(spreadEditor)


  def createColor(random: Random = new Random, gaussian: Boolean = true): Vec4 = {
    RandomUtils.hslColor(base = Vec4(hue(), sat(), lum(), 1f),
                         spread = Vec4(hueVariation(), satVariation(), lumVariation(), 0f),
                         gaussian = gaussian,
                         random = random)
  }
}