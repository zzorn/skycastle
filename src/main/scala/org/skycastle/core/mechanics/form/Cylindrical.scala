package org.skycastle.core.mechanics.form

import org.skycastle.core.entity.Facet

/**
 * Indicates that something is round and longish with some diameter.
 * Specifies variance.
 * Can be cut to some length.
 * Can be either flexible or rigid, rigid ones can be used as axles, handles for tools, etc, and flexible ones to tie things together and so on.
 */
case class Cylindrical(length_m: Double, diameter_m: Double, tolerance_m: Double) extends Form {
  def volume_m3 = Math.Pi * (0.5*diameter_m) * (0.5*diameter_m) * length_m

  def shape = null // TODO: Cylinder, add noise depending on tolerance?  Or specify noise amplitude param?  What about flexible cylinders like rope that is stored as a coil?

}