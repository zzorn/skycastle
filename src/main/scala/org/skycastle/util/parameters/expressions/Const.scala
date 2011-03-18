package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * Constant expression
 */
case class Const(constant: Any) extends Expr {
  def calculate(context: Parameters) = constant
}