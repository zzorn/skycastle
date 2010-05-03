package org.skycastle.server.core

import _root_.org.skycastle.server.Message

/**
 * Provides a message sending interface.
 */
trait MessageSender {

  /**
   * Send a message to the controller of this agent (player or ai).
   */
  def sendMessage(message: Message) {
    // TODO
  }

}