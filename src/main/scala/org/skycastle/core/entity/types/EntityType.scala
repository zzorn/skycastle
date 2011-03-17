package org.skycastle.core.entity.types

import org.skycastle.core.entity.Entity
import org.skycastle.util.parameters.Parameters

/**
 * Information about a type of entity.
 * Used to create actual instances of it.
 */
// TODO: Maybe rename to archetype or similar, to get shorter names and an atomic concept
case class EntityType(name: Symbol, entityParameters: Parameters = Parameters(), facetTypes: List[FacetType] = Nil) {

  def createInstance(instanceParameters: Parameters): Entity = {
    val entity = new Entity()
    facetTypes.foreach({facetType =>
        facetType.addFacetInstanceTo(entity, instanceParameters, entityParameters)
    })

    entity
  }

}