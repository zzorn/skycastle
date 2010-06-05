package org.skycastle.shared.entity

import _root_.org.skycastle.shared.model.Entity
import _root_.org.skycastle.shared.persistence.Ref

/**
 * A part of an entity.
 */
trait Facet extends Persistent {

  type ReferenceType = Facet

  private var _entity: Ref[Entity] = null

  /**
   * The entity that this facet is a part of.
   */
  def entity: Ref[Entity] = _entity

  private[entity] def entity_=(entity: Ref[Entity]) = _entity = entity
}

