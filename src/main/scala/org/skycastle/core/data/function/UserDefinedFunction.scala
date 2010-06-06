package org.skycastle.core.data.function

import org.skycastle.core.data.{Data, Value}

/**
 * 
 */
case class UserDefinedFunction (body: Value) extends DataFunction {

  def apply(parameters: Data): Value = {

    // Replace any parameter references in the body with the actual parameter value
    val parametrizedBody = body.replaceParameterReferences(parameters)

    // Invoke any functions in the body that needs to be invoked
    val calculatedBody = parametrizedBody.invokeFunctions()

    // Return result body
    calculatedBody
  }

}