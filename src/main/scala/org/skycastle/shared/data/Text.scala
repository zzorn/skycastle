package org.skycastle.shared.data

/**
 * Text / String data value.
 */
class Text extends AbstractValue {

  def this(text: String) {
    this()
    value = text
  }
  
  override type Self = this.type
  type T = String
  def self = this

  def defaultValue = ""

  override def isText = true
  override def asString = value

}