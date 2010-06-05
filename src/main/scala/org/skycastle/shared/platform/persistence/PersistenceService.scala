package org.skycastle.shared.platform.persistence

/**
 * 
 */
trait PersistenceService {
  def createReference[T <: Persistent](obj: T): Ref[T]
  def markForUpdate(obj: Persistent)

  def store(obj: Persistent)
  def delete(obj: Persistent)

  def get(name: String): Persistent
  def getForUpdate(name: String): Persistent
  def bind(name: String, obj: Persistent)
  def removeBinding(name: String)


}

