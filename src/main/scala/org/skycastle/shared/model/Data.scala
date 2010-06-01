package org.skycastle.shared.model

/**
 * 
 */
// TODO: Make this represent a map instead, more efficient as every leaf values doesn't have to be a wrapped primitive.
trait Data {

  def apply(member: Symbol*): Data

  def add(index: Symbol, member: Data)
  def add(member: Data): Unit = add(nextFreeIndex, member)
  def remove(member: Data): Unit  = remove(indexOf(member))
  def remove(index: Symbol)

  def indexOf(data: Data): Symbol
  def nextFreeIndex: Symbol
  def hasValue: Boolean
  def hasMember(index: Symbol): Boolean

  def value: Object
  def value(default: Object): Object = if (hasValue) value else default

  def := (value: Object)

  def addListener(listener: (Data)=>Unit )
  def removeListener(listener: (Data)=>Unit )

  def asBoolean(): Boolean
  def asInt(): Int
  def asLong(): Long
  def asFloat(): Float
  def asDouble(): Double
  def asString(): String

  def asBoolean(default: Boolean): Boolean
  def asInt(default: Int): Int
  def asLong(default: Long): Long
  def asFloat(default: Float): Float
  def asDouble(default: Double): Double
  def asString(default: String): String

}

