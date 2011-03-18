package org.skycastle.core.design

import org.skycastle.util.parameters.Parameters
import org.skycastle.util.parameters.expressions.{Const, Expr}

/**
 * A design that instantiates as one or other concrete design based on some parameter.
 */
class AlternativeDesign extends Design {

  var test: Expr = Const(true)

  var designIf = EmptyDesign
  var designOtherwise = EmptyDesign

  def build(parameters: Parameters) = {
    // Evaluate test on current context.
    if (test.bool(parameters)) designIf.create(parameters)
    else designOtherwise.create(parameters)
  }

}