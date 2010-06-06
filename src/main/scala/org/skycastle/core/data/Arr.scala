package org.skycastle.core.data

/**
 * An array / list of Data values.
 */
class Arr extends AbstractValue {

  def this(first: Value, rest: Value*) {
    this()
    add(first)
    addAll(rest.toList)
  }

  type T = List[Value]
  type Self = this.type
  def defaultValue = Nil
  def self = this

  override def asList = value
  override def isList = true

  def add(element: Value) = value = value ::: List(element)
  def addFirst(element: Value) = value = element :: value
  def addAll(elements: List[Value]) = value = value ::: elements
  def remove(element: Value) = value = value.filterNot(e => e == element)
  def contains(element: Value) = value.contains(element)
  def clear() = value = Nil
}
