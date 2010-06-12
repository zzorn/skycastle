package org.skycastle.core.data

import collection.mutable.StringBuilder

/**
 * Text / String data value.
 */
case class Text(value: String) extends Value {

  override def prettyPrint(out: StringBuilder, indent: Int) {
    out.append("\"" + value + "\"")// TODO: Escape special chars
  }

/*
  override def toString: String = "\"" + value + "\"" // TODO: Escape special chars
*/

}