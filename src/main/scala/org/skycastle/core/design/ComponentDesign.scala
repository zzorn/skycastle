package org.skycastle.core.design

import org.skycastle.core.entity.types.EntityTypeManager
import org.skycastle.util.Parameters

/**
 * A design for a specific type of basic component.
 * May be parametrized.
 */
class ComponentDesign extends Design {

  val entityType = p('entityType, null)
  val entityParameters = p[Parameters]('entityParameters, Parameters())

  def create() = EntityTypeManager.createEntity(entityType(), entityParameters())

}