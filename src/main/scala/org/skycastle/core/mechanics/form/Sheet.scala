package org.skycastle.core.mechanics.form

import org.skycastle.core.entity.Facet

/**
 * Indicates that some object is a flat sheet of material.  Can be cut to some shape.
 */
case class Sheet(width_m: Double, length_m: Double, thickness_m: Double, tolerance_m: Double) extends Form {
  def volume_m3 = width_m * length_m * thickness_m

  def shape = null // TODO: thin block, add noise depending on tolerance?  Or specify noise amplitude param?  What about flexible sheets like canvas that is stored as a roll?
}