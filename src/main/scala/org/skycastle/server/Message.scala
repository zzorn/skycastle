package org.skycastle.server

import _root_.java.io.Serializable
import activity.ActivitySessionId
import util.Parameters

/**
 * A message from the client to a specific activity that the player is joined in on the server side,
 * or from an activity on the server to a specific (UI) component on the client (or AI component of an autonomous agent?).
 *
 * TODO: Make the content be a JSON object or similar?
 * TODO: Sender and target could be (a possibly nested list of) proxy id:s?
 *
 * Id is the action to call, or perception received.
 */
// TODO: Make content a map with named values
final case class Message(activitySession: ActivitySessionId, action: Symbol, parameters: Parameters) {
  
}

