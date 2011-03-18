package org.skycastle.core.entity


/**
 * A game object.
 */
class Entity {

  private var facets: Map[Manifest[_ <: Facet], Facet] = Map()

  def addFacet[T <: Facet](facet: T)(implicit m: Manifest[T]) {
    removeFacet(facet)
    facet.entity = this
    facets += (m -> facet)
  }

  def removeFacet[T <: Facet](facet: T)(implicit m: Manifest[T]) {
    facet.entity = null
    facets -= m
  }

  def getFacet[T <: Facet](implicit m: Manifest[T]): Option[T] = facets.get(m).asInstanceOf[Option[T]]
  
  def facet[T <: Facet](implicit m: Manifest[T]): T = {
    val facetOption = facets.get(m)
    if (facetOption.isDefined) facetOption.get.asInstanceOf[T]
    else throw new FacetNotFoundException(m, "No facet of the type exists in entity " + this + ", can not get it.")
  }

  def withFacet[T <: Facet](op: T => Unit)(implicit m: Manifest[T]) = {
    getFacet[T](m).foreach(f => op(f) )
  }

  def update(timeDelta: Float) {
    facets.values foreach (facet => facet.update(timeDelta))
  }

}



