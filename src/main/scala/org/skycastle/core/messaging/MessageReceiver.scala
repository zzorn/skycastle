package org.skycastle.core.messaging

import _root_.org.skycastle.core.data.Data

/**
 * Something that can receive messages (from the client or server)
 */
trait MessageReceiver {

  // TODO: pass just Value, and provide derived receivers that unpack various types of messages if needed (e.g. action name and params)
  def onMessage(message: Data)

}

