package org.skycastle.server

import _root_.java.io.Serializable

/**
 * Keeps track of Activity info that is local to a specific Agent.
 */
trait ActivityProxy extends Serializable {

  def handleMessage(message: Message)
  
}

