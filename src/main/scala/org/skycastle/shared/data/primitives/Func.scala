package org.skycastle.shared.data.primitives

import _root_.org.skycastle.shared.data.Value

/**
 * A function that takes a value as input and returns a value.
 */
final case class Func {

  /**
   * Runs the calculation for the specified input.
   * Throws an exception if it could not find the expected values in the input.
   */
  def calculate(parameter: Value): Value
}
