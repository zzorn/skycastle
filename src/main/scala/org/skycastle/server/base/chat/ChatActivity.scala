package org.skycastle.server.base

import _root_.org.skycastle.server.activity.{ActivitySessionId, ActivitySession}
import _root_.org.skycastle.server.util.{Ref, Parameters}
import _root_.org.skycastle.server.{ActivityProxy, Activity, Agent}

/**
 * Basic chat activity.
 */
class ChatActivity extends Activity {
  override protected def createActivityProxy(sessionId: ActivitySessionId, parameters: Parameters, session: ActivitySession, agent: Agent): ActivityProxy = {
    new ChatActivityProxy(sessionId, parameters, Ref(this), Ref(agent))
  }

}

