package org.skycastle.core.entity

import _root_.org.skycastle.core.platform.persistence.{Ref, Persistent}
import org.skycastle.util.ListenableList

/**
 * A persistent object consisting of different parts (facets).
 */
trait Entity extends Persistent {

  private var _facets = new ListenableList[Ref[Facet]](true, true)

  def addFacet(facet: Facet) {
    _facets.add(facet.ref)
    facet.entity = this.ref
  }

  def removeFacet(facet: Facet) {
    _facets.remove(facet.ref)
    facet.entity = null
  }

  def facets: List[Ref[Facet]] = _facets()

  protected override type DerivedType = Entity
  protected override def asDerivedType: DerivedType = this


}

