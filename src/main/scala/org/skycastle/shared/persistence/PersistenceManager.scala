package org.skycastle.shared.persistence

import _root_.org.skycastle.shared.entity.Persistent

/**
 * 
 */

trait PersistenceManager {
  def store(obj: Persistent)
  def delete(obj: Persistent)

  def createReference[T <: Persistent](obj: T): Ref[T]

  def get(name: String): Persistent
  def getForUpdate(name: String): Persistent
  def bind(name: String, obj: Persistent)
  def removeBinding(name: String)
}

