package org.skycastle.core.design

import org.skycastle.core.entity.types.EntityTypeManager
import org.skycastle.util.parameters.Parameters

/**
 * A design for a specific type of basic component.
 * May be parametrized.
 */
class ComponentDesign extends Design {

  var entityType: Symbol = null
  var entityParameters = Parameters()

  def create(context: Parameters) = {
    // TODO: Map context parameters to entity paramters?
    EntityTypeManager.createEntity(entityType, entityParameters)
  }

}