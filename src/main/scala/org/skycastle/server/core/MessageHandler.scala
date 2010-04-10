package org.skycastle.server.core

import _root_.org.skycastle.server.Message

/**
 * Something that handles messages from a controller (AI or player client), or from an activity.
 */
trait MessageHandler {

  /**
   * Handle the message if possible.  Return true if handled, false if the message was not handled (e.g. not recognized).
   */
  def handleMessage(message: Message): Boolean = false
}

