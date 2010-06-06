package org.skycastle.core.data

import function.DataFunction

/**
 * A function that creates some value when called with some parameters.
 */
class Fun extends AbstractValue {

  var defaultParameters: Data = new Data()
  var metadata: Data = null

  type Self = this.type
  def self = this

  type T = DataFunction
  def defaultValue = null

  // TODO: use default parameters for values where the call doesn't specify parameters
  def invoke(parameters: Data): Value = if (value == null) new Data() else value(parameters)

}