package org.skycastle.shared.entity

import _root_.org.skycastle.shared.platform.persistence.{Ref, Persistent}

/**
 * A persistent object consisting of different parts (facets).
 */
trait Entity extends Persistent {

  private var _facets: List[Ref[Facet]] = Nil

  def addFacet(facet: Facet) {
    _facets = (facet.ref) :: _facets
    facet.entity = this.ref
  }

  def removeFacet(facet: Facet) {
    val f = facet.ref
    _facets = _facets.filterNot(e => e == f)
    facet.entity = null
  }

  def facets: List[Ref[Facet]] = _facets

  protected override type DerivedType = Entity
  protected override def asDerivedType: DerivedType = this


}

