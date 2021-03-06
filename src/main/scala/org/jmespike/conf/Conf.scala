package org.jmespike.conf

import java.awt.Color
import org.scalaprops.ui.editors.{SliderFactory, ColoredSliderBackgroundPainter}
import org.scalaprops.ui.editors.SliderFactory._
import org.scalaprops.{Property, Bean}
import org.jmespike.utils.ColorUtils

/**
 * Base class for settings that can be loaded and saved to config and edited.
 */
// TODO: Some kinda primitive value for a random variable, with some given probability field, and editor for it
trait Conf extends Bean {

  implicit def propertyToValue[T](prop: Property[T]): T = prop.value


  def makeSlider(start: Double = 0, end: Double = 1, hue: Double = 0.5, sat: Double = 0.2, lum: Double = 0.4): SliderFactory[Float] = {
    val col = ColorUtils.HSLtoRGB(hue.toFloat, sat.toFloat, lum.toFloat, 1f)
    val painter = new ColoredSliderBackgroundPainter(Color.WHITE, new Color(col.r, col.g, col.b))
    new SliderFactory[Float](start.toFloat,
                             end.toFloat,
                             restrictNumberFieldMin = false,
                             restrictNumberFieldMax = false,
                             backgroundPainter = painter)
  }

}
