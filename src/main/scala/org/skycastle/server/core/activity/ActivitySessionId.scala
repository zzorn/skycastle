package org.skycastle.server.activity

import _root_.java.io.Serializable

/**
 * Unique ID for a specific session on a specific activity of some agent.
 */
final case class ActivitySessionId(agentSessionId: Long, activitySessionId: Long) extends Serializable

