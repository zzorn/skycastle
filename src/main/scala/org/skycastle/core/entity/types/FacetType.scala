package org.skycastle.core.entity.types

import org.skycastle.core.entity.{Entity, Facet}

/**
 * Information about a type of facet, used in some EntityType.
 * This class is used to create instances of the Facet.
 */
case class FacetType(facetName: Symbol, facetParameters: Map[Symbol, Any] = Map()) {

  def addFacetInstanceTo(entity: Entity, instanceParameters: Map[Symbol, Any], entityParameters: Map[Symbol, Any]) {

    val (facet: Facet, manifest: Manifest[_]) = FacetManager.createFacet(facetName).getOrElse(throw new IllegalStateException("Facet type " + facetName + " not found."))

    // TODO: Init first and add then, or the other way?
    entity.addFacet(facet)(manifest)
    facet.init(instanceParameters, entityParameters, facetParameters)
  }

}