package org.skycastle.core.entity.types

import org.skycastle.util.parameters.Parameters
import org.skycastle.core.entity.{Facet, Entity}
import org.skycastle.util.Logging

/**
 * Information about a type of entity.
 * Used to create actual instances of it.
 */
// TODO: Some unique ID for entities?
case class Archetype(name: Symbol, defaultParameters: EntityParameters) extends Logging {

  /**
   * Creates an entity instance of the archetype, using all default parameters.
   */
  def createEntity(): Entity = createEntity(null)

  /**
   * Creates an entity instance of the archetype.
   * The instanceParameters are parameters to pass to the different facets,
   * overriding the provided default parameters.
   */
  def createEntity(instanceParameters: EntityParameters): Entity = {
    // Combine instance parameters with default parameters when available
    val parameters = if (instanceParameters != null) instanceParameters chain defaultParameters
    else defaultParameters

    // Create entity
    val entity = new Entity()
    entity.entityName = name.name + "-" + entity.hashCode

    // Create and add facets of the entity
    val facetCategories = parameters.getFacetCategories
    facetCategories.foreach({facetCategory =>
      val facetParameters: Parameters = parameters.getFacetParameters(facetCategory)
      val facet = createFacet(facetCategory, facetParameters)
      facet.init(facetParameters)
      entity.setFacet(facet)
    })

    // Log creation
    logDebug("Created instance of entity "+ name.name + " with parameters: "+parameters)

    entity
  }

  private def createFacet(facetCategory: Symbol, facetParameters: Parameters): Facet = {
    val kind = facetParameters.getSymbol('type, null)
    if (kind == null) throw new IllegalStateException("Could not create facet for category '" + facetCategory.name + "', no type specified in parameters.")
    Registry.createType(facetCategory, kind)
  }

}