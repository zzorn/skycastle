package org.jmespike.conf

import org.scalaprops.Bean
import util.Random
import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.scalaprops.ui.editors.{ColoredSliderBackgroundPainter, SliderFactory}
import java.awt.Color
import org.jmespike.conf.Conf
import org.jmespike.utils.{ColorUtils, RandomUtils}
import com.jme3.math.ColorRGBA
import org.jmespike.utils.VectorConversions._

/**
 *
 */
class ColorConf() extends Conf {

  private val baseEditor: SliderFactory[Float] = makeSlider(hue=0.1, lum = 0.2)
  private val spreadEditor: SliderFactory[Float] = makeSlider(end = 0.5, hue=0.7, lum = 0.6)

  // TODO: Separate UI name for beans, beanName is used when loading
  //beanName = Symbol(_name)

  val hue = p('hue, 0f).editor(baseEditor)
  val sat = p('sat, 0.5f).editor(baseEditor)
  val lum = p('lum, 0.5f).editor(baseEditor)
  val alpha = p('alpha, 1f).editor(baseEditor)

  def getColor: Vec4 = ColorUtils.HSLtoRGB(hue, sat, lum, alpha)
  def getJmeColor: ColorRGBA = getColor
}