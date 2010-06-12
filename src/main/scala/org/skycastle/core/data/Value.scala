package org.skycastle.core.data

import java.io.Serializable

/**
 * 
 */
trait Value extends Serializable {

  def replaceParameterReferences(parameters: Data): Value = this

  def invokeFunctions(): Value = this

  def prettyPrint(out: StringBuilder, indent: Int) 

  override def toString: String = {
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