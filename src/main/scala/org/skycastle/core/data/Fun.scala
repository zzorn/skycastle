package org.skycastle.core.data

import collection.mutable.StringBuilder
import context.DataContext

object Fun {
  type Parameter = Tuple2[Symbol, Option[Value]]
}

/**
 * A function that creates some value when called with some parameters.
 */
case class Fun(parameters: List[Fun.Parameter], body: Value) extends Value {

  def invoke(context: DataContext, parameters: Data): Value  = {
    // Extend the local context with actual parameter values, or default values if not specified
    // TODO

    // Evaluate the body and return it
    // TODO

    throw new java.lang.UnsupportedOperationException("Not implemented")
  }

  override def evaluate(context: DataContext): Value = {
    // Extend the context with the parameters?
    // TODO

    // Evaluate any function calls that don't take parameters
    // TODO

    throw new java.lang.UnsupportedOperationException("Not implemented")
  }

  override def prettyPrint(out: StringBuilder, indent: Int) {
    def printParams(ps: List[Fun.Parameter]) {
      if (ps != Nil) {
        out.append( ps.head._1.name )
        ps.head._2 match {
          case None =>
          case Some(x) => {
             out.append(": ")
             x.prettyPrint(out, indent)
          }
        }

        if (ps.tail != Nil) {
          out.append(", ")
          printParams(ps.tail)
        }
      }
    }

    out.append("function")
    out.append("(")
    printParams(parameters)
    out.append(") ")
    body.prettyPrint(out, indent)
  }

}