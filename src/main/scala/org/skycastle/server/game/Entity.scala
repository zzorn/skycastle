package org.skycastle.server.game

import _root_.com.sun.sgs.app.ManagedObject
import _root_.java.io.Serializable
import _root_.org.skycastle.server.util.Ref

/**
 * An entity consisting of components providing different functionality.
 */
trait Entity extends ManagedObject with Serializable {

  val id: Long

  private var _facets: List[Ref[Facet]] = Nil

  def facets = _facets

  def addFacet(facet: Facet) {
    if (facet == null) throw new IllegalArgumentException("Can not add null facet to entity " + id);

    val facetRef = Ref(facet)
    if (_facets.contains(facetRef)) throw new IllegalArgumentException("Can not add facet '"+facet+"', it is already contained in entity " + id);

    _facets += facetRef

    facet.setEntity(this)
  }

  def removeFacet(facetRef: Ref[Facet]) {
    if (facet == null) throw new IllegalArgumentException("Can not remove null facet to entity " + id);

    if (!_facets.contains(facetRef)) throw new IllegalArgumentException("Can not remove facet '"+facet+"', it is not contained in entity " + id);

    facetRef.getForUpdate.setEntity(null)

    _facets -= facetRef
  }


}

