package org.skycastle.core.data

import function.DataFunction

object Fun {
  type Parameter = Tuple2[Symbol, Option[Value]]
}

/**
 * A function that creates some value when called with some parameters.
 */
case class Fun(parameters: List[Fun.Parameter], body: Value) {

  // TODO: use default parameters for values where the call doesn't specify parameters
  def invoke(parameters: Data): Value = null // TODO
}