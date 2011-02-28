package org.skycastle.core.mechanics.form

import org.skycastle.core.entity.Facet

/**
 * The form of an object. 
 */
trait Form extends Facet {
  def volume_m3: Double
  def tolerance_m: Double
//  def shape: Shape
}