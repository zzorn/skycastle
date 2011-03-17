package org.skycastle.util.parameters.expressions

import org.skycastle.util.parameters.Parameters

/**
 * Something that can calculate a value, based on a parameter context.
 */
trait Expression {

  def calculate(context: Parameters): Any


}