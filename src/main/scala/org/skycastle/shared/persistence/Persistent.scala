package org.skycastle.shared.entity

import _root_.com.sun.sgs.app.ManagedObject
import _root_.org.skycastle.shared.persistence.{PersistenceManagerAccessor, PersistenceManager}

/**
 * Storable object.
 */
trait Persistent extends ManagedObject {

  def persistenceManager: PersistenceManager = PersistenceManagerAccessor.persistenceManager

  def delete() = persistenceManager.delete(this)
  def store() = persistenceManager.store(this)

}

