package org.skycastle.core.data

import collection.mutable.StringBuilder

/**
 * A reference to some value.
 */
case class Link(path: List[Symbol] ) extends Value {

  override def prettyPrint(out: StringBuilder, indent: Int) {
    out.append(path.map(_.name).mkString("."))
  }

}

