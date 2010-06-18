package org.skycastle.core.mechanics.form

/**
 * A round form.
 * Can be anything from a ball bearing or a rough pebble (variable tolerance).
 */
case class Ball(diameter_m: Double, tolerance_m: Double) extends Form {
  def volume_m3 = Math.Pi * (4.0 / 3.0) * (0.5*diameter_m) * (0.5*diameter_m) * (0.5*diameter_m)

  def shape = null // TODO: Sphere, add noise depending on tolerance?  Or specify noise amplitude param?
}
