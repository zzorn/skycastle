package org.skycastle.core.data

import context.DataContext

/**
 * An array / list of Data values.
 */
case class Arr(values: List[Value]) extends Value {

  override def evaluate(context: DataContext): Value = {
    // Evaluate each memeber and create a list from them
    Arr(values.map(_.evaluate(context)))
  }

  override def prettyPrint(out: StringBuilder, indent: Int) {
    out.append("[")
    printValues(values, ", ", out, indent)
    out.append("]")
  }


}
