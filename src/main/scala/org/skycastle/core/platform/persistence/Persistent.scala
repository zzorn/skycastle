package org.skycastle.core.platform.persistence

import _root_.com.sun.sgs.app.{ManagedObject}
import _root_.java.io.Serializable
import _root_.org.skycastle.core.platform.SkycastleContext

/**
 * Storable object.
 */
trait Persistent extends ManagedObject with Serializable {

  def ref[T <: Persistent]: Ref[T] = persistenceServices.createReference(this.asInstanceOf[T])
  def markAsModified() = persistenceServices.markForUpdate(this)
  def delete() = persistenceServices.delete(this)
  def store() = persistenceServices.store(this)

  private def persistenceServices: PersistenceService = SkycastleContext.platformServices
  
}

