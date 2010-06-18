package org.skycastle.core.mechanics.form

import org.skycastle.core.entity.Facet

/**
 * Indicates that an object is a cubic block that can be carved or cut to some shape.
 */
case class Block(width_m: Double, height_m: Double, depth_m: Double, tolerance_m: Double) extends Form {
  def volume_m3 = width_m * height_m * depth_m

  def shape = null // TODO: Block, add noise depending on tolerance?  Or specify noise amplitude param?
  
}