package org.skycastle.util.parameters

import com.jme3.math.ColorRGBA
import expressions.{Var, Expr}

/**
 * A set of immutable parameters, with some typed convenience getters.
 */
case class Parameters(parameters: Map[Symbol, Any]) {

  def getString(name: Symbol, default: String): String = {
    parameters.getOrElse(name, default).toString
  }

  def getColor(name: Symbol, default: ColorRGBA): ColorRGBA = {
    if (!parameters.contains(name)) default else {
      val c: Map[Symbol, Any] = parameters(name).asInstanceOf[Map[Symbol, Any]]
      new ColorRGBA(
        c.getOrElse('r, 0f).asInstanceOf[Number].floatValue,
        c.getOrElse('g, 0f).asInstanceOf[Number].floatValue,
        c.getOrElse('b, 0f).asInstanceOf[Number].floatValue,
        c.getOrElse('a, 1f).asInstanceOf[Number].floatValue)
    }
  }

  def getSymbol(name: Symbol, default: Symbol): Symbol = {
    Symbol(getString(name, default.name))
  }

  def getFloat(name: Symbol, default: Float): Float = {
    parameters.getOrElse(name, default).asInstanceOf[Number].floatValue
  }

  def getInt(name: Symbol, default: Int): Int = {
    parameters.getOrElse(name, default).asInstanceOf[Int]
  }

  def remap(expressions: Map[Symbol, Expr]): Parameters = {
    Parameters(parameters.map( {(e: (Symbol, Any)) =>
      (e._1, expressions.getOrElse(e._1, Var(e._1).calculate(this)))
    }))
  }

  def chain(secondary: Parameters): Parameters = {
    new Parameters(secondary.parameters ++ parameters)
  }

}

object Parameters  {

  def apply(): Parameters = EmptyParameters

}

object EmptyParameters extends Parameters(Map())