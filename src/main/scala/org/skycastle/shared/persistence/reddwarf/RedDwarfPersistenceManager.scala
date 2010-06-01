package org.skycastle.shared.persistence

import _root_.com.sun.sgs.app.{AppContext, DataManager, ManagedReference, ManagedObject}
import _root_.java.lang.String
import _root_.org.skycastle.shared.entity.Persistent
import reddwarf.RedDwarfRef

/**
 * 
 */
object RedDwarfPersistenceManager extends PersistenceManager{

  def createReference[T <: Persistent](obj: T) = new RedDwarfRef(dataManager.createReference(obj))

  def store(obj: Persistent) = dataManager.createReference(obj)
  def delete(obj: Persistent) = dataManager.removeObject(obj)

  def bind(name: String, obj: Persistent) = dataManager.setBinding(name, obj)
  def get(name: String) = dataManager.getBinding(name).asInstanceOf[Persistent]
  def getForUpdate(name: String) = dataManager.getBindingForUpdate(name).asInstanceOf[Persistent]
  def removeBinding(name: String) = dataManager.removeBinding(name)

  private def dataManager = AppContext.getDataManager
  private def taskManager = AppContext.getTaskManager
}

