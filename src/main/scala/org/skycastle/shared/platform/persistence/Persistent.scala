package org.skycastle.shared.platform.persistence

import _root_.com.sun.sgs.app.{ManagedObject}
import _root_.java.io.Serializable
import _root_.org.skycastle.shared.platform.SkycastleContext

/**
 * Storable object.
 */
trait Persistent extends ManagedObject with Serializable {

  def ref: Ref[DerivedType] = persistenceServices.createReference(asDerivedType)
  def markAsModified() = persistenceServices.markForUpdate(this)
  def delete() = persistenceServices.delete(this)
  def store() = persistenceServices.store(this)

  private def persistenceServices: PersistenceService = SkycastleContext.platformServices

  protected type DerivedType <: Persistent
  protected def asDerivedType: DerivedType

}

