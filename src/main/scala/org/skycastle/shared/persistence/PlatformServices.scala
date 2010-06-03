package org.skycastle.shared.persistence

import _root_.com.sun.sgs.app.Task
import _root_.org.skycastle.shared.entity.Persistent
import _root_.org.skycastle.shared.Time

/**
 * 
 */

trait PlatformServices {
  def createReference[T <: Persistent](obj: T): Ref[T]
  def markForUpdate(obj: Persistent)

  def store(obj: Persistent)
  def delete(obj: Persistent)

  def get(name: String): Persistent
  def getForUpdate(name: String): Persistent
  def bind(name: String, obj: Persistent)
  def removeBinding(name: String)

  def scheduleCallback(time: Time, callback: Task)

}

