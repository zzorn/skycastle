package org.skycastle.server

/**
 * Represents an internal object on te server (typically a managed object) that can
 * receive events.
 */
trait Entity {
  def handleEvent(event: Event)
}

