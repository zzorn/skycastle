package org.skycastle.server

import _root_.com.sun.sgs.app.{ManagedObject, AppContext}
import _root_.java.io.Serializable
import util.Ref

/**
 * Represents an internal object on the server.
 *
 * Also hides the internal implementation of the server and acts as a gateway to it,
 * allowing entities to be deployed either on a RedDwarf server, or in a simple container on a local client.
 */
trait Entity extends ManagedObject with Serializable {

  /**
   * Notifies the system that this entity has changes to its values, and should be stored.
   */
  def markForUpdate() {
    AppContext.getDataManager.markForUpdate(this)
  }

}

