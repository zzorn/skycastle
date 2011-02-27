package org.skycastle.core.entity.types

import org.skycastle.core.entity.Entity

/**
 * Information about a type of entity.
 * Used to create actual instances of it.
 */
case class EntityType(name: Symbol, entityParameters: Map[Symbol, Any] = Map(), facetTypes: List[FacetType] = Nil) {

  def createInstance(instanceParameters: Map[Symbol, Any]): Entity = {
    val entity = new Entity()
    facetTypes.foreach({facetType =>
        val facet = facetType.createInstance(instanceParameters, entityParameters)
        entity.addFacet(facet)
    })

    entity
  }

}