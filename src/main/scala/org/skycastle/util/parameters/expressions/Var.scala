package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * Expression which value is a variable lookup in the context.
 */
case class Var(variable: Symbol) extends Expr {
  def calculate(context: Parameters) = context.parameters(variable)
}
