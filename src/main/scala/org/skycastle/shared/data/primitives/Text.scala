package org.skycastle.shared.data

/**
 * String Value.
 */
final case class Text(value: String) extends Value {
  def stringValue: String = value
}

