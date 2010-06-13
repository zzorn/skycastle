package org.skycastle.core.data

import context.{EmptyDataContext, DataContext}
import java.io.Serializable

/**
 * 
 */
trait Value extends Serializable {

  def replaceParameterReferences(parameters: Data): Value = this

  def invokeFunctions(): Value = this

  def prettyPrint(out: StringBuilder, indent: Int)


  /**
   * Evaluates the value by invoking any function calls.
   */
  final def evaluate(): Value = evaluate(EmptyDataContext)

  /**
   * Evaluates the value by invoking any function calls.
   * @param context Defines the context for the value, that is, the other visible named value from the point of this value.
   */
  def evaluate(context: DataContext): Value = this


  override final def toString: String = {
    val s = new StringBuilder()
    prettyPrint(s, 0)
    s.toString
  }

  protected def dent(out: StringBuilder, indent: Int): StringBuilder = {
    var i = indent
    out.append("\n")
    while (i > 0) { out.append("  "); i -= 1 }
    out
  }
  
  protected def printValues(vs: List[Value], separator: String, out: StringBuilder, indent: Int) {
    if (vs != Nil) {
      vs.head.prettyPrint(out, indent)
      val rest = vs.tail
      if (rest != Nil) {
        out.append(separator)
        printValues(rest, separator, out, indent)
      }
    }
  }

}