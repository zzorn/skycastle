package org.skycastle.client.appearance

import org.scalaprops.Bean
import com.jme3.math.ColorRGBA

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

  def toColorRGBA: ColorRGBA = new ColorRGBA(r(), g(), b(), a())
}