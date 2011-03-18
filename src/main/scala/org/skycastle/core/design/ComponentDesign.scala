package org.skycastle.core.design

import org.skycastle.core.entity.types.EntityTypeManager
import org.skycastle.util.parameters.Parameters

/**
 * A design for a specific type of basic component.
 * May be parametrized.
 */
class ComponentDesign extends Design {

  def build(parameters: Parameters) = {
    EntityTypeManager.createEntity(
      parameters.getSymbol('entityType, 'placeholder),
      parameters)
  }

}