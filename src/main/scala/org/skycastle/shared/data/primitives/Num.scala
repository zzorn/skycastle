package org.skycastle.shared.data

import _root_.java.lang.Number

/**
 * Represents a numerical value.
 */
final case class Num(value: Num) {
  def intValue: Int = value.intValue
  def longValue: Long = value.longValue
  def floatValue: Float = value.floatValue
  def doubleValue: Double = value.doubleValue
}

