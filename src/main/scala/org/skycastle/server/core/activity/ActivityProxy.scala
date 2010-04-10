package org.skycastle.server

import _root_.java.io.Serializable
import activity.ActivitySessionId
import core.MessageHandler
import util.{Parameters, Ref}

/**
 * Keeps track of Activity info that is local to a specific Agent.
 *
 * Serialized as part of the Agent managed object.
 */
// TODO: Possible to listen to the methods (and streams) provided by the activity for the current agent? -> some way for the activity to send messages to the Agent.
class ActivityProxy(val sessionId: ActivitySessionId, val parameters: Parameters, val activity: Ref[Activity], val agent: Ref[Agent]) extends MessageHandler with Serializable 

