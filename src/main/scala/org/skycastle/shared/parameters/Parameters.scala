package org.skycastle.server.util

/**
 * Used as content in messages.  Holds named values of various types.
 */
class Parameters {
  def getString(name: Symbol): Option[String] = None
  def getString(name: Symbol, default: String): String = getString(name).getOrElse(default)
}

