package org.skycastle.core.data

import function.FunctionCall

/**
 * A function call.
 */
// TODO: A call to a list with an index returns the element at the index,
// and a call to a map with a string returns the element with the name -> support those also.
class Call extends AbstractValue {

  type Self = this.type
  def self = this

  type T = FunctionCall
  def defaultValue = null

  def invoke(): Value = value.invoke()

}