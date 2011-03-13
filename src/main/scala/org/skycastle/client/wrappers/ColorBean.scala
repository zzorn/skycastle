package org.skycastle.client.wrappers

import org.scalaprops.Bean
import com.jme3.math.ColorRGBA

/**
 * Wraps a color, allowing it to be read from configuration files.
 */
class ColorBean extends Bean {

  val r = p('r, 0f)
  val g = p('g, 0f)
  val b = p('b, 0f)
  val a = p('a, 1f)

  setBeanName('Color)

  def this(red: Float, green: Float, blue: Float, alpha: Float = 1f) {
    this()
    r := red
    g := green
    b := blue
    a := alpha
  }

  def this(color: ColorRGBA) {
    this()
    r := color.r
    g := color.g
    b := color.b
    a := color.a
  }

  def toColorRGBA: ColorRGBA = new ColorRGBA(r(), g(), b(), a())
}