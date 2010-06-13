package org.skycastle.core.entity

import _root_.java.lang.Class
import _root_.org.skycastle.core.data.Data
import _root_.org.skycastle.core.platform.SkycastleContext
import org.skycastle.core.space.simple.SimpleSpace
import org.skycastle.core.space.Item
import org.skycastle.core.appearance.Appearance

class FacetServiceImpl extends FacetService {

  private var _facetTypes: Map[Symbol, Class[_ <: Facet]] = null

  def facetTypes: Map[Symbol, Class[_ <: Facet]] = {
    // Initialize bound facets first time we use them
    if (_facetTypes == null) {
      _facetTypes = Map()
      initFacetTypes()
    }

    _facetTypes
  }

  def getFacetType(facetName: Symbol) = facetTypes.get(facetName)
  def hasFacetType(facetName: Symbol) = facetTypes.contains(facetName)
  def bindFacetType(facetName: Symbol, facetType: Class[_ <: Facet]) = _facetTypes = facetTypes + (facetName -> facetType)

  def createFacet(facetName: Symbol, parameters: Data): Option[_ <: Facet] = {
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


  private def initFacetTypes() = {

    bindFacetType('SimpleSpace, classOf[SimpleSpace])
    bindFacetType('Item, classOf[Item])
    bindFacetType('Appearance, classOf[Appearance])

  }
}

