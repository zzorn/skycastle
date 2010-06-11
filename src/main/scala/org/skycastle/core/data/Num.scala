package org.skycastle.core.data

/**
 * Double number.
 */
case class Num(value: Double) extends Value {

  override def clamp(min: Double) : Double = Math.max(min, value)
  override def clamp(min: Double, max: Double) : Double = Math.max(min, Math.min(max, value))

}