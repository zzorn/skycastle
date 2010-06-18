package org.skycastle.core.mechanics.form

import org.skycastle.core.shape.Shape

/**
 * A custom more specialized form.
 * Can not be used as raw material for various crafting processes,
 * but is instead some organic or human created artifact.
 */
case class Shaped(shape: Shape) extends Form {

  def tolerance_m = 0.0

  def volume_m3 = shape.volume_m3

}