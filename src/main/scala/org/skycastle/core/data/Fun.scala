package org.skycastle.core.data

import function.DataFunction

object Fun {
  type Parameter = Tuple2[Symbol, Option[Value]]
}

/**
 * A function that creates some value when called with some parameters.
 */
case class Fun(parameters: List[Fun.Parameter], body: Value) extends Value {

  // TODO: use default parameters for values where the call doesn't specify parameters
  def invoke(parameters: Data): Value = null // TODO

  private def paramPrint(p: Fun.Parameter): String = {
    p._1.name +
    (p._2 match {
      case None => ""
      case Some(x) => ": " + x
    })
  }
  override def toString: String = "function(" + parameters.map(paramPrint).mkString(", ") + ") " + body
}