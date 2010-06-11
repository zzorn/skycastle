package org.skycastle.core.data

/**
 * An array / list of Data values.
 */
case class Arr(values: List[Value]) extends Value {
  override def toString: String = values.mkString("[", ", ", "]")
}
