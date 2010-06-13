package org.skycastle.core.data

import collection.mutable.StringBuilder
import context.DataContext

/**
 * A reference to some value.
 */
case class Link(parameterReference: Boolean, path: List[Symbol] ) extends Value {

  override def evaluate(context: DataContext): Value = {

    // Find the value the link refers to
    val referedValue = context.getEvaluatedValue(path) // TODO: Handle parameter references?

    // TODO: add own exception type for 'compiling'
    if (referedValue.isEmpty) throw new IllegalStateException("Could not find the value of the link " + toString)
    else referedValue.get
  }



  override def prettyPrint(out: StringBuilder, indent: Int) {
    if (parameterReference) out.append("$")
    out.append(path.map(_.name).mkString("."))
  }

}

