package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * Allows parameters to be calculated from other parameters with matemathical and logical expressions.
 */
class ParametersExpression(var expressions: Map[Symbol, Exp]) {

  def calculate(context: Parameters): Parameters = context.remap(expressions)

}