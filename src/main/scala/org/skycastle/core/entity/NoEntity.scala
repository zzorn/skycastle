package org.skycastle.core.entity

/**
 * Placeholder Nil Entity.
 */
object NoEntity extends Entity {

  override def setFacet(name: Symbol, newFacet: Facet): Unit = {
    throw new UnsupportedOperationException("EmptyEntity can not have facets added")
  }

}