package org.skycastle.shared.entity

import _root_.org.skycastle.shared.platform.persistence.{Ref, Persistent}
import _root_.org.skycastle.shared.platform.scheduler.Taskable
import org.skycastle.shared.data.Data

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

  /**
   * Called when after the Facet has been created.
   */
  protected def init(parameters: Data)

  def isInitialized = true // TODO: Set after init has been called
}

