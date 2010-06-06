package org.skycastle.core.data

/**
 * 
 */
abstract class AbstractValue extends Value {

  private var _value: T = defaultValue

  final def apply(): T = _value
  final def := (value: T) = _value = value


}