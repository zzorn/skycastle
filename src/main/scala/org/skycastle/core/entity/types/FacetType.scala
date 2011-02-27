package org.skycastle.core.entity.types

import org.skycastle.core.entity.Facet

/**
 * Information about a type of facet, used in some EntityType.
 * This class is used to create instances of the Facet.
 */
case class FacetType(facetClass: Class[_ <: Facet], facetParameters: Map[Symbol, Any] = Map()) {

  def createInstance(instanceParameters: Map[Symbol, Any], entityParameters: Map[Symbol, Any]): Facet = {
    val facet = facetClass.newInstance()
    facet.init(instanceParameters, entityParameters, facetParameters)
  }

}