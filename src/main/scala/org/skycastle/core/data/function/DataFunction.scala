package org.skycastle.core.data.function

import org.skycastle.core.data.{Value, Data}

/**
 * 
 */
trait DataFunction {

  def apply(parameters: Data): Value

}