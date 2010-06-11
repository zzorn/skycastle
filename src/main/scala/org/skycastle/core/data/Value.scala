package org.skycastle.core.data

import java.io.Serializable

/**
 * 
 */
trait Value extends Serializable {

  def replaceParameterReferences(parameters: Data): Value = this

  def invokeFunctions(): Value = this

}