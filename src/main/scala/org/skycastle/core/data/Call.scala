package org.skycastle.core.data

import function.FunctionCall

/**
 * A function call.
 */
class Call extends AbstractValue {

  type Self = this.type
  def self = this

  type T = FunctionCall
  def defaultValue = null

  def invoke(): Value = value.invoke()

}