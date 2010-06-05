package org.skycastle.shared.entity

import _root_.org.skycastle.shared.platform.persistence.{Ref, Persistent}
import _root_.org.skycastle.shared.platform.scheduler.Taskable

/**
 * A part of an entity, concentrating on a specific area of functionality.
 */
trait Facet extends Persistent with Taskable {

  private var _entity: Ref[Entity] = null

  /**
   * The entity that this facet is a part of.
   */
  def entity: Ref[Entity] = _entity

  def entity_=(entity: Ref[Entity]) = _entity = entity

  protected override type DerivedType = Facet
  protected override def asDerivedType: DerivedType = this

}

