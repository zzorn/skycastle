package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * 
 */
case class LessThan(a: Exp, b: Exp) extends Exp {
  def calculate(context: Parameters) =
    a.calculateNum(context).doubleValue <
    b.calculateNum(context).doubleValue

}