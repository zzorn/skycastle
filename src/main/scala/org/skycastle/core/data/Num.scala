package org.skycastle.core.data

import scala.math._

/**
 * Double number.
 */
case class Num(value: Double) extends Value {

  def clamp(minValue: Double) : Double = max(minValue, value)
  def clamp(minValue: Double, maxValue: Double) : Double = max(minValue, min(maxValue, value))

  override def toString: String = value.toString
  
}