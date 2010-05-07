package org.skycastle.server.game

import _root_.com.sun.sgs.app.{ManagedObject, AppContext}
import _root_.java.io.Serializable
import _root_.org.skycastle.server.util.Ref
import _root_.org.skycastle.shared.util.Checker


/**
 * A part / component of an entity, concentrating on simulating some part of it.
 *
 * Facets can have properties / info channels that are perceivable by other entities, or by the
 * controller controlling an entity.  Aspects also can have actions that can be initiated by the
 * controller controlling the entity?
 */
trait Facet extends ManagedObject with Serializable {

  // TODO: Implement a listenable list with verifiers and and listeners, in that case we could just have three public vals here instead of all this stuff.
  private var _properties: List[Property] = Nil
  private var _actions: List[Action] = Nil
  private var _feeds: List[Feed] = Nil

  def properties: List[Property] = _properties
  def actions: List[Action] = _actions
  def feeds: List[Feed] = _feeds

  protected def addProperty(property: Property) = Checker.addIfNotNullAndNotContained(_properties, property, "The property")
  protected def addAction(action: Action) = Checker.addIfNotNullAndNotContained(_actions, action, "The action")
  protected def addFeed(feed: Feed) = Checker.addIfNotNullAndNotContained(_feeds, feed, "The feed")

  protected def removeProperty(property: Property) = _properties -= property
  protected def removeAction(action: Action) = _actions -= action
  protected def removeFeed(feed: Feed) = _feeds -= feed

  private var _entity: Ref[Entity] = null

  def entity = _entity

  def setEntity(entity: Entity) {
    val oldEntityRef = _entity
    _entity = if(entity==null) null else Ref(entity)

    if (oldEntityRef != null) onRemovedFromEntity(oldEntityRef)
    if (entity!= null) onAddedToEntity(entity)
  }

  
  protected def onAddedToEntity(entity: Entity) {}
  protected def onRemovedFromEntity(entityRef: Ref[Entity]) {}



    /**
     * Notifies the system that this facet has changes to its values, and should be stored.
     */
    def markForUpdate() {
      AppContext.getDataManager.markForUpdate(this)
    }

}

