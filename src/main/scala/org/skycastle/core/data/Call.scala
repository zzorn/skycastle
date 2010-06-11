package org.skycastle.core.data

import function.FunctionCall

/**
 * A function call.
 */
// TODO: Change first param to Value - can be function call, link, map, array
// TODO: A call to a list with an index returns the element at the index,
// and a call to a map with a string returns the element with the name -> support those also.
case class Call(link: Link, parameters: List[(Option[Symbol], Value)]) extends Value {

  def invoke(): Value = {
    // TODO

    null
  }

}