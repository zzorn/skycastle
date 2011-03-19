package org.skycastle.core.entity.types

import org.skycastle.core.entity.Facet


/**
 * Stores the kinds of facets that are available.
 * @Deprecated
 */
object FacetManager {

  private var facetKinds: Map[Symbol, Class[_ <: Facet]] = Map()

  def registerFacetKind[T <: Facet](kind: Class[T]) {
    registerFacetKind(kind, Symbol(kind.getSimpleName))
  }

  def registerFacetKind[T <: Facet](kind: Class[T], name: Symbol) {
    println("registering facet " + name)
    facetKinds += (name -> kind)
  }

  def hasFacet(name: Symbol): Boolean = facetKinds.contains(name)

  def createFacet(name: Symbol): Option[Facet] = {
    facetKinds.get(name) match {
      case None => None
      case Some(kind) => Some(kind.newInstance().asInstanceOf[Facet])
    }
  }


}
