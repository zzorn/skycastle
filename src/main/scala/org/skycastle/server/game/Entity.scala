package org.skycastle.server.game

import com.sun.sgs.app.ManagedObject
import org.skycastle.server.util.Ref
import java.io.Serializable

/**
 * An entity consisting of components providing different functionality.
 */
trait Entity extends ManagedObject  with Serializable{

  val id: Long

  private var _facets: List[Ref[Facet]] = Nil

  def facets = _facets

  def addFacet(facet: Facet) {
    if (facet == null) throw new IllegalArgumentException("Can not add null facet to entity " + id);

    val facetRef = Ref(facet)
    if (_facets.contains(facetRef)) throw new IllegalArgumentException("Can not add facet '"+facet+"', it is already contained in entity " + id);

    _facets = _facets ::: List(facetRef)

    facet.setEntity(this)
  }

  def removeFacet(facetRef: Ref[Facet]) {
    if (facetRef == null) throw new IllegalArgumentException("Can not remove null facet to entity " + id);

    if (!_facets.contains(facetRef)) throw new IllegalArgumentException("Can not remove facet '"+facetRef+"', it is not contained in entity " + id);

    facetRef.getForUpdate.setEntity(null)

    _facets = _facets.remove(x => x == facetRef) 
  }


}

