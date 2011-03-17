package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * 
 */
case class And(a:  Exp, b: Exp) extends Exp {
  def calculate(context: Parameters) = a.calculateBoolean(context) && b.calculateBoolean(context)
}