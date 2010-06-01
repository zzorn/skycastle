package org.skycastle.shared.persistence

import _root_.java.lang.String
import _root_.java.util.{HashSet, ArrayList, HashMap}
import _root_.org.skycastle.shared.entity.Persistent
import memory.MemoryRef

/**
 * 
 */

object InMemoryPersistenceManager extends PersistenceManager {

  private val objects: HashSet[Persistent] = new HashSet()
  private val namedObjects: HashMap[String, Persistent] = new HashMap()

  def createReference[T <: Persistent](obj: T) = new MemoryRef(obj)

  def store(obj: Persistent) = objects.add(obj)
  def delete(obj: Persistent) = objects.remove(obj)

  def get(name: String) = namedObjects.get(name)
  def getForUpdate(name: String) = namedObjects.get(name)
  def bind(name: String, obj: Persistent) = namedObjects.put(name, obj)
  def removeBinding(name: String) = namedObjects.remove(name)
}

