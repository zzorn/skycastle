package org.skycastle.shared.data

/**
 * Represents some data value.
 */
trait Value {
  def subValue(index: Symbol): Option[Value]
}

