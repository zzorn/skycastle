package org.skycastle.core.entity

/**
 * Placeholder Nil Entity.
 */
object EmptyEntity extends Entity {

  override def addFacet[ T <: Facet ](facet: T)(implicit m: Manifest[ T ]) =
    throw new UnsupportedOperationException("EmptyEntity can not have facets added")

}