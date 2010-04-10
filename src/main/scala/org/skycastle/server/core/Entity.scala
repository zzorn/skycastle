package org.skycastle.server

import _root_.com.sun.sgs.app.{ManagedObject, AppContext}
import _root_.java.io.Serializable

/**
 * Represents an internal object on the server.
 */
trait Entity extends ManagedObject with Serializable {

  /**
   * Notifies the system that this entity has changes to its values, and should be stored.
   */
  def markForUpdate() {
    AppContext.getDataManager.markForUpdate(this)
  }
}

