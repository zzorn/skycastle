package org.skycastle.server.activity

import _root_.java.io.Serializable
import _root_.org.skycastle.server.util.{Parameters, Ref}
import _root_.org.skycastle.server.{Message, Agent}

/**
 * Information about an agents session with an activity, stored in the activity managed object.
 */
class ActivitySession(val sessionId: ActivitySessionId, parameters: Parameters, val agent: Ref[Agent]) extends Serializable {

  def sendMessage(action: Symbol, content: Parameters ) {
    agent().sendMessage(new Message(sessionId, action, content))
  }
}

