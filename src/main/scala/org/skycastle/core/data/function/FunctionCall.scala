package org.skycastle.core.data.function

import org.skycastle.core.data.{Value, Data, Fun}

/**
 * 
 */
case class FunctionCall(function: Fun, parameters: Data) {

  def invoke(): Value = {
    function.invoke(parameters)
  }

}