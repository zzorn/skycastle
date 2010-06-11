package org.skycastle.core.data

/**
 * Text / String data value.
 */
case class Text(value: String) extends Value {
  override def toString: String = "\"" + value + "\"" // TODO: Escape special chars

}