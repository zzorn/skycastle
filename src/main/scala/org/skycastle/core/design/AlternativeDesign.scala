package org.skycastle.core.design

import org.skycastle.util.parameters.Parameters

/**
 * A design that instantiates as one or other concrete design based on some parameter.
 */
class AlternativeDesign extends Design {

  var test = "false"

  var designIf = EmptyDesign
  var designOtherwise = EmptyDesign

  def create(context: Parameters) = {
    // TODO: Evaluate test on current context.
    if (test == "true") designIf.create(context)
    else designOtherwise.create(context)
  }
}