package org.skycastle.shared.data.primitives

import _root_.org.skycastle.shared.data.{Index, Value}

object EmptyPath extends Path(Nil)

/**
 * Used as a reference to specific values, by being a list of value indexes in the collections they are located.
 */
case class Path(path: List[Index]) extends Value {
  def head = path.head
  def tail = Path(path.tail)
  def hasTail = !path.isEmpty && !path.tail.isEmpty
  def hasHead = !path.isEmpty

  def isEmpty = path.isEmpty

  def throwInvalidPath(): Nothing = {
    throw new IndexOutOfBoundsException("Invalid path: '" + this + "'")
  }

  override def toString = path.mkString(".")
}

