package org.skycastle.core.entity

import _root_.java.lang.Class
import _root_.org.skycastle.core.data.Data
import _root_.org.skycastle.core.platform.SkycastleContext

class FacetServiceImpl extends FacetService {

  private var facetTypes: Map[Symbol, Class[Facet]] = Map()

  def getFacetType(facetName: Symbol) = facetTypes.get(facetName)
  def hasFacetType(facetName: Symbol) = facetTypes.contains(facetName)
  def bindFacetType(facetName: Symbol, facetType: Class[Facet]) = facetTypes += facetName -> facetType

  def createFacet(facetName: Symbol, parameters: Data): Option[Facet] = {
    if (!hasFacetType(facetName)) None
    else {
      try {
        val facet = facetTypes(facetName).newInstance
        facet.initialize(parameters)
        SkycastleContext.platformServices.store(facet)
        return Some(facet)
      }
      catch {
        case e: InstantiationException => e.printStackTrace; None // TODO: Log
        case e: IllegalAccessException => e.printStackTrace; None // TODO: Log
      }
    }
  }
}

