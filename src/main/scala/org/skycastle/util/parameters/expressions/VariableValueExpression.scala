package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * 
 */
case class VariableValueExpression(variable: Symbol) extends Expression {
  def calculate(context: Parameters) = context.parameters(variable)
}