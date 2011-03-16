package org.skycastle.core.design

/**
 * A design that instantiates as one or other concrete design based on some parameter.
 */
class IfDesign extends Design {

  val test = p('test, "false")

  val designIf = p('designIf, EmptyDesign)
  val designOtherwise = p('designOtherwise, EmptyDesign)

  def create() = {
    // TODO: Evaluate test on current context.
    if (test() == "true") designIf().create()
    else designOtherwise().create()
  }
}