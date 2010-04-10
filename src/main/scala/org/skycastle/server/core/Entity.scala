package org.skycastle.server

import _root_.com.sun.sgs.app.AppContext

/**
 * Represents an internal object on the server (typically a managed object) that can
 * receive events.
 */
trait Entity {
  
  // TODO: Do we need this for anything, or can we just call methods instead?  Maybe for async calling?
  def handleEvent(event: Event) {}

  def markForUpdate() {
    AppContext.getDataManager.markForUpdate(this)
  }
}

