package org.skycastle.core.entity.types

import org.skycastle.core.entity.{Entity, Facet}
import org.skycastle.util.parameters.Parameters

/**
 * Information about a type of facet, used in some EntityType.
 * This class is used to create instances of the Facet.
 */
case class FacetType(facetName: Symbol, facetParameters: Parameters = Parameters()) {

  def addFacetInstanceTo(entity: Entity, instanceParameters: Parameters, entityParameters: Parameters) {

    val facet: Facet = FacetManager.createFacet(facetName).getOrElse(throw new IllegalStateException("Facet type " + facetName + " not found."))

    // TODO: Init first and add then, or the other way?
    entity.setFacet(facet)
    facet.init(instanceParameters, entityParameters, facetParameters)
  }

}