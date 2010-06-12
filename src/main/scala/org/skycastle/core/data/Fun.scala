package org.skycastle.core.data

import collection.mutable.StringBuilder

object Fun {
  type Parameter = Tuple2[Symbol, Option[Value]]
}

/**
 * A function that creates some value when called with some parameters.
 */
case class Fun(parameters: List[Fun.Parameter], body: Value) extends Value {

  // TODO: use default parameters for values where the call doesn't specify parameters
  def invoke(parameters: Data): Value = null // TODO

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

/*
  private def paramPrint(p: Fun.Parameter): String = {
    p._1.name +
    (p._2 match {
      case None => ""
      case Some(x) => ": " + x
    })
  }
  override def toString: String = "function(" + parameters.map(paramPrint).mkString(", ") + ") " + body
*/
}