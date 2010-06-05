package org.skycastle.shared.entity

import _root_.com.sun.sgs.app.{ManagedObject}
import _root_.java.io.Serializable
import _root_.org.skycastle.shared.model.Data
import _root_.org.skycastle.shared.persistence.{Ref, SkycastleContext, PlatformServices}
import _root_.org.skycastle.shared.tasks.{Callback}
import _root_.org.skycastle.shared.Time

/**
 * Storable object.
 */
trait Persistent extends ManagedObject with Serializable {

  type ReferenceType <: Persistent

  def ref: Ref[ReferenceType] = platformServices.createReference[ReferenceType](this)
  def markAsModified() = platformServices.markForUpdate(this)
  def delete() = platformServices.delete(this)
  def store() = platformServices.store(this)

  def scheduleCallback(time: Time, parameters: Data) = platformServices.scheduleCallback(time, new Callback(this, parameters))

  /**
   * Method that gets called when a callback event occurs.
   */
  def callback(parameters: Data) {}

  protected def platformServices: PlatformServices = SkycastleContext.platformServices



}

