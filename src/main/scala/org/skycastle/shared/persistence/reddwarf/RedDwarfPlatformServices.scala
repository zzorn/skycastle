package org.skycastle.shared.persistence

import _root_.com.sun.sgs.app._
import _root_.java.lang.String
import _root_.org.skycastle.shared.entity.Persistent
import _root_.org.skycastle.shared.Time
import reddwarf.RedDwarfRef

/**
 * 
 */
object RedDwarfPlatformServices extends PlatformServices{

  def createReference[T <: Persistent](obj: T) = new RedDwarfRef(dataManager.createReference(obj))
  def markForUpdate(obj: Persistent) = dataManager.markForUpdate(obj)
  def store(obj: Persistent) = dataManager.createReference(obj)
  def delete(obj: Persistent) = dataManager.removeObject(obj)

  def bind(name: String, obj: Persistent) = dataManager.setBinding(name, obj)
  def get(name: String) = dataManager.getBinding(name).asInstanceOf[Persistent]
  def getForUpdate(name: String) = dataManager.getBindingForUpdate(name).asInstanceOf[Persistent]
  def removeBinding(name: String) = dataManager.removeBinding(name)

  def scheduleCallback(time: Time, callback: Task) = taskManager.scheduleTask(callback, SkycastleContext.timeService.delayTo(time) )

  private def dataManager = AppContext.getDataManager
  private def taskManager = AppContext.getTaskManager
}

