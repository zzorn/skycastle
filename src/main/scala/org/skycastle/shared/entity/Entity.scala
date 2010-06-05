package org.skycastle.shared.model

import _root_.org.skycastle.shared.entity.{Persistent, Facet}
import _root_.org.skycastle.shared.persistence.Ref

/**
 * 
 */
trait Entity extends Persistent {

  type ReferenceType = Entity

  private var _facets: List[Ref[Facet]] = Nil

  def addFacet(facet: Facet) {
    _facets = (facet.ref) :: _facets
    facet.entity = this.ref
  }

  def removeFacet(facet: Facet) {
    val f = facet.ref
    _facets = _facets.remove( e => e == f)
    facet.entity = null
  }

  def facets: List[Ref[Facet]] = _facets


}

