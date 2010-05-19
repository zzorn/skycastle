package org.skycastle.shared.model

/**
 * 
 */

trait Data {

  def apply(): Object
  def update(value: Object)

  def addListener(listener: (Data)=>Unit )
  def removeListener(listener: (Data)=>Unit )

  def asBoolean(): Boolean
  def asInt(): Int
  def asLong(): Long
  def asFloat(): Float
  def asDouble(): Double
  def asString(): String

}

