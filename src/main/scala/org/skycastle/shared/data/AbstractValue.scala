package org.skycastle.shared.data

/**
 * 
 */
abstract class AbstractValue extends Value {

  private var _value: T = defaultValue
  def self: Self

/* TODO: Replace with creating new immutable copy if this is mutable
*/
  def immutable: Self = self

  def apply(): T = _value
  def := (value: T) = _value = value


}