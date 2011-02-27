package org.skycastle.core.entity.types

import org.skycastle.core.entity.Facet


/**
 * Stores the types of facets that are available.
 */
object FacetManager {

  private var facets: Map[Symbol, Manifest[_ <: Facet]] = Map()

  def registerFacetType[T <: Facet](implicit m: Manifest[T]) {
    val sym = Symbol(m.erasure.getSimpleName)

    println("registering facet " + sym.name)
    facets += (sym -> m)
  }

  def createFacet(name: Symbol): Option[(Facet, Manifest[Facet])] = {
    facets.get(name) match {
      case None => None
      case Some(m) => Some((m.erasure.newInstance().asInstanceOf[Facet], m.asInstanceOf[Manifest[Facet]]))
    }
  }


}
