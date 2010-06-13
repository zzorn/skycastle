package org.skycastle.core.entity

import _root_.org.skycastle.core.platform.persistence.{Ref, Persistent}
import _root_.org.skycastle.core.platform.scheduler.Taskable
import org.skycastle.core.data.Data


/**
 * A part of an entity, concentrating on a specific area of functionality.
 */
trait Facet extends Persistent with Taskable {

  private var _entity: Ref[Entity] = null

  /**
   * The entity that this facet is a part of.
   */
  def entity: Ref[Entity] = _entity
  def entity_=(newEntity: Ref[Entity]) = {
    val oldEntity = _entity
    _entity = newEntity
    onEntityChanged(oldEntity, newEntity)
  }

  protected[entity] final def initialize(parameters: Data) {init(parameters)}

  /**
   * Called when after the Facet has been created.
   */
  protected def init(parameters: Data) {}

  /**
   * Called when the entity that this facet is in has changed.
   */
  protected def onEntityChanged(oldEntity: Ref[Entity], newEntity: Ref[Entity]) {}

}

