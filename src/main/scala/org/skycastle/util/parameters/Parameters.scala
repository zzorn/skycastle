package org.skycastle.util.parameters

import com.jme3.math.ColorRGBA
import expressions.{Variable, Exp}

/**
 * A set of immutable parameters, with some typed convenience getters.
 */
case class Parameters(parameters: Map[Symbol, Any]) {

  def stringParam(name: Symbol, default: String): String = {
    parameters.getOrElse(name, default).toString
  }

  def colorParam(name: Symbol, default: ColorRGBA): ColorRGBA = {
    if (!parameters.contains(name)) default else {
      val c: Map[Symbol, Any] = parameters(name).asInstanceOf[Map[Symbol, Any]]
      new ColorRGBA(
        c.getOrElse('r, 0f).asInstanceOf[Number].floatValue,
        c.getOrElse('g, 0f).asInstanceOf[Number].floatValue,
        c.getOrElse('b, 0f).asInstanceOf[Number].floatValue,
        c.getOrElse('a, 1f).asInstanceOf[Number].floatValue)
    }
  }

  def symbolParam(name: Symbol, default: Symbol): Symbol = {
    Symbol(stringParam(name, default.name))
  }

  def floatParam(name: Symbol, default: Float): Float = {
    parameters.getOrElse(name, default).asInstanceOf[Number].floatValue
  }

  def intParam(name: Symbol, default: Int): Int = {
    parameters.getOrElse(name, default).asInstanceOf[Int]
  }

  def remap(expressions: Map[Symbol, Exp]): Parameters = {
    Parameters(parameters.map( {(e: (Symbol, Any)) =>
      (e._1, expressions.getOrElse(e._1, Variable(e._1).calculate(this)))
    }))
  }

}

object Parameters  {

  def apply(): Parameters = EmptyParameters

}

object EmptyParameters extends Parameters(Map())