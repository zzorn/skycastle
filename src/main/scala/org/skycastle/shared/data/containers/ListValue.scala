package org.skycastle.shared.data

/**
 * Contains a sequence of values of any type.
 */
class ListValue extends Value {
  def values: List[Value]
  def entry(index: Int): Value
}

