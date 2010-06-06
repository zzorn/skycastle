package org.skycastle.core.data

/**
 * 
 */

class Num extends AbstractValue {

  def this(number: Number) {
    this()
    value = number
  }

  type Self = this.type
  type T = Number
  def self = this

  def defaultValue = 0.0

  override def isNumber = true

  override def asInt : Int = value.intValue
  override def asLong : Long = value.longValue
  override def asFloat : Float = value.floatValue
  override def asDouble : Double = value.doubleValue
}