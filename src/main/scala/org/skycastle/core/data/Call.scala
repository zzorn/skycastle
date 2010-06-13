package org.skycastle.core.data

import context.DataContext

/**
 * A function call.
 */
// TODO: Change first param to Value - can be function call, link, map, array
// TODO: A call to a list with an index returns the element at the index,
// and a call to a map with a string returns the element with the name -> support those also.
case class Call(function: Value, parameters: List[(Option[Symbol], Value)]) extends Value {

  override def evaluate(context: DataContext): Value = {
    // Extend the context with the parameters
    val extendedContext = context // TODO

    // Evaluate the body with the extended context
    function.evaluate(extendedContext)
  }



  override def prettyPrint(out: StringBuilder, indent: Int) {
    def printParams(ps: List[(Option[Symbol], Value)]) {
      if (ps != Nil) {
        out.append( ps.head._1.toList.map(_.name + ": ").mkString )
        ps.head._2.prettyPrint(out, indent)

        if (ps.tail != Nil) {
          out.append(", ")
          printParams(ps.tail)
        }
      }
    }

    function.prettyPrint(out, indent)
    out.append("(")
    printParams(parameters)
    out.append(")")
  }

}