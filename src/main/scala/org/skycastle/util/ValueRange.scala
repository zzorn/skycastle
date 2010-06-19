package org.skycastle.util

/**
 * A range from some value to some other.
 */
// TODO: Add ranges that are open up or downwards
case class ValueRange(min: Double, max: Double) {

  def contains(v: Double) = v >= min && v <= max

}