package org.skycastle.core.entity

import _root_.org.skycastle.core.data.Data

/**
 * Keeps track of the facets that can be created when loading data.
 */
trait FacetService {

  def bindFacetType(facetName: Symbol, facetType: Class[_ <: Facet])
  def hasFacetType(facetName: Symbol): Boolean
  def getFacetType(facetName: Symbol): Option[Class[_ <: Facet]]

  /**
   * Creates a new facet of the specified type and with the specified parameters.
   * Stores the facet in the persistence service.
   */
  def createFacet(facetName: Symbol, parameters: Data): Option[_ <: Facet]

}

