package org.skycastle.server.game


/**
 * A part / component of an entity, concentrating on simulating some part of it.
 *
 * Facets can have properties / info channels that are perceivable by other entities, or by the
 * controller controlling an entity.  Aspects also can have actions that can be initiated by the
 * controller controlling the entity?
 */
trait Facet {

  def actions: List[Action]

  def feeds: List[Feed]

  def properties: List[Property]
  
}

