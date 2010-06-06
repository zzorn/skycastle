package org.skycastle.shared.data

/**
 * 
 */

class Bool extends AbstractValue {
  
  def this(bool: Boolean) {
    this()
    value = bool
  }

  type Self = this.type
  type T = Boolean
  def self = this

  def defaultValue = false

  override def isBoolean = true
  override def asBoolean = value
}
