package org.skycastle.core.platform.persistence.memory

import _root_.java.lang.String
import _root_.org.skycastle.core.platform.persistence.{Persistent, PersistenceService}
import java.util.{Collections, HashSet, HashMap}

/**
 * Local memory implemetation of platform services.
 */
class InMemoryPersistenceService extends PersistenceService {

  private val objects: HashSet[Persistent] = new HashSet()
  private val namedObjects: HashMap[String, Persistent] = new HashMap()

  def createReference[T <: Persistent](obj: T) = new MemoryRef(obj)
  def markForUpdate(obj: Persistent) = {}
  def store(obj: Persistent) = objects.add(obj)
  def delete(obj: Persistent) = objects.remove(obj)

  def get(name: String) = namedObjects.get(name)
  def getForUpdate(name: String) = namedObjects.get(name)
  def bind(name: String, obj: Persistent) = namedObjects.put(name, obj)
  def removeBinding(name: String) = namedObjects.remove(name)

  def storedObjects = Collections.unmodifiableSet(objects)
  def numberOfObjects: Int = objects.size
}

