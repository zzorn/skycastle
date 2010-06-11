package org.skycastle.core.data

/**
 * A reference to some value.
 */
case class Link(path: List[Symbol] ) extends Value {

  override def toString: String = path.map(_.name).mkString(".")
  
}

