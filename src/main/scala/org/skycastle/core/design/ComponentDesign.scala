package org.skycastle.core.design

import org.skycastle.util.parameters.Parameters
import org.skycastle.core.entity.types.{EntityParameters, ArchetypeManager}

/**
 * A design for a specific type of basic component.
 * May be parametrized.
 */
class ComponentDesign extends Design {

  def this(parameters: Map[Symbol, Any]) {
    this()
    defaultParameters = Parameters(parameters)
  }

  def build(parameters: Parameters) = {
    // Expand parameters to entity parameters, by treating dot separated prefixes as the facet categories..
    val entityParameters = new EntityParameters(parameters)
    val archetypeName = parameters.getSymbol('archetype, 'placeholder)
    ArchetypeManager.createEntity(archetypeName, entityParameters)
  }

}