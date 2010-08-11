package org.skycastle.core.platform.persistence.reddwarf

import _root_.com.sun.sgs.app._
import _root_.java.lang.String
import _root_.org.skycastle.core.platform.persistence.{Persistent, PersistenceService}

/**
 * Implementation of PersistenceService that uses the RedDwarf middleware library.
 */
object RedDwarfPersistenceService extends PersistenceService {

  def createReference[T <: Persistent](obj: T) = new RedDwarfRef(dataManager.createReference(obj))
  def markForUpdate(obj: Persistent) = dataManager.markForUpdate(obj)
  def store(obj: Persistent) = dataManager.createReference(obj)
  def delete(obj: Persistent) = dataManager.removeObject(obj)

  def bind(name: String, obj: Persistent) = dataManager.setBinding(name, obj)
  def get(name: String) = dataManager.getBinding(name).asInstanceOf[Persistent]
  def getForUpdate(name: String) = dataManager.getBindingForUpdate(name).asInstanceOf[Persistent]
  def removeBinding(name: String) = dataManager.removeBinding(name)

  private def dataManager = AppContext.getDataManager
}
