package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * Tries to perform a numerical addition.
 * If some of the operands are not numbers, results in an error string.
 */
case class Add(a: Exp, b: Exp) extends Exp {
  def calculate(context: Parameters) =
    a.calculateNum(context).doubleValue +
    b.calculateNum(context).doubleValue
}