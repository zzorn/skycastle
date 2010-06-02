package org.skycastle.shared.entity

import _root_.com.sun.sgs.app.{Task, ManagedObject}
import _root_.javax.xml.crypto.Data
import _root_.org.skycastle.shared.persistence.{SkycastleContext, PersistenceManager}
import _root_.org.skycastle.shared.tasks.{Callback, TaskManager}
import _root_.org.skycastle.shared.Time

/**
 * Storable object.
 */
trait Persistent extends ManagedObject {

  def persistenceManager: PersistenceManager = SkycastleContext.persistenceManager
  def taskManager: TaskManager = SkycastleContext.taskManager

  def delete() = persistenceManager.delete(this)
  def store() = persistenceManager.store(this)

  /**
   * Method that gets called when a callback event occurs.
   */
  def callback(parameters: Data) {}

  def scheduleCallback(time: Time, parameters: Data) = taskManager.scheduleCallback(time, new Callback(this, parameters))


}

