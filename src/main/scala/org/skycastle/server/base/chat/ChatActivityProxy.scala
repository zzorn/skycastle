package org.skycastle.server.base

import _root_.org.skycastle.server.activity.ActivitySessionId
import _root_.org.skycastle.server.util.{Parameters, Ref}
import _root_.org.skycastle.server.{Agent, Message, ActivityProxy, Activity}
/**
 * User specific code and data for a member in a chat.
 */
// TODO: Initialize the proxy with some init method instead of on construction, so we don't have to pass all this stuff every time?
class ChatActivityProxy(_sessionId : ActivitySessionId, _parameters: Parameters, _activity : Ref[Activity], _agent: Ref[Agent]) extends ActivityProxy(_sessionId, _parameters, _activity, _agent) {

  private var _nick: String = parameters.getString('nick, null)

  private def nick: String = {
    if (_nick == null) {
      _nick = "unknown" // TODO: Autogenerate unique nick here - for that we need a reference to the ChatActivity -> generify reference to it..
    }
    _nick
  }

  override def handleMessage(message: Message): Boolean = {
    message.action match {
      case 'say =>
        val text = message.parameters.getString('text, "")
        val from = nick
        activity().sendMessageToAll('sayPerception, new Parameters())//TODO: Pass text and from params
        true
      case _ => false
    }
  }
  
}

