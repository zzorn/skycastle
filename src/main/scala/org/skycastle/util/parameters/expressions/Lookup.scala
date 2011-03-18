package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * Looks up a variable value based on the name in another variable
 */
class Lookup(variableName: Symbol, defaultVariable: Symbol) extends Expr {
  def calculate(context: Parameters) = context.parameters(context.getSymbol(variableName, defaultVariable))
}
