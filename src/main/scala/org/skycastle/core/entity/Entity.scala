package org.skycastle.core.entity

import org.skycastle.client.appearance.Appearance
import org.skycastle.core.space.{Space, Position}

/**
 * A game object.
 */
class Entity {

  private var _facets: Map[Symbol, Facet] = Map()

  var entityName: String = toString

  /**
   * The facets defined for this entity.
   */
  def facets: Map[Symbol, Facet] = _facets

  /**
   * The appearance of the entity.
   */
  def appearance: Appearance = facet('appearance)
  def appearance_=(appearance: Appearance) = setFacet('appearance, appearance)

  /**
   * The position of the entity.
   */
  def position: Position = facet('position)
  def position_=(position: Position) = setFacet('position, position)

  /**
   * The internal space of an entity.
   */
  def space: Space = facet('space)
  def space_=(space: Space) = setFacet('space, space)

  /**
   * Updates the entity components.
   */
  def update(timeDelta: Float) {
    facets.values foreach (_.update(timeDelta))
  }

  def facet[T <: Facet](name: Symbol): T = {
    facets.get(name) match {
      case Some(facet) => facet.asInstanceOf[T]
      case None => throw new FacetNotFoundException("No facet named '"+name.name+"' available in entity " + entityName)
    }
  }

  def getFacet(name: Symbol): Option[Facet] = facets.get(name)

  def setFacet(facet: Facet): Unit = setFacet(facet.facetName, facet)

  def setFacet(name: Symbol, newFacet: Facet): Unit = {
    _facets.get(name).foreach(_.entity = null)

    if (newFacet != null) {
      newFacet.entity = this
      _facets += name -> newFacet
    }
  }

}



