package org.skycastle.core.shape

import org.skycastle.core.data.Data

/**
 * 
 */
case class ShapeDefinition(definition: Data) {

  def name:String  = definition.getString('name, "unnamed")

  def create(): Shape = create(Data(Map()))

  def create(parameters: Data): Shape = {
    // TODO

    null
  }

  override def toString = name
}