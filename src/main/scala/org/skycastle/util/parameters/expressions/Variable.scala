package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * 
 */
case class Variable(variable: Symbol) extends Exp {
  def calculate(context: Parameters) = context.parameters(variable)
}