package org.skycastle.shared.data

/**
 * Represents a numerical value.
 */
trait NumericalValue {
  def intValue: Int
  def longValue: Long
  def floatValue: Float
  def doubleValue: Double
}

