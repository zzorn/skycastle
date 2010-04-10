package org.skycastle.server

import _root_.com.sun.sgs.app.{AppContext, ManagedObject}
import _root_.java.io.Serializable
import activity.{ActivitySessionId, ActivityJoinException, ActivitySession}
import util.{Parameters, IdSequence, Ref}

/**
 * Something that Agents can join.
 *
 * Can be used for game modes, lobbies, vehicles, minigames, world places (outdoor/room), admin controls, and so on.
 *
 * Provides a local proxy for the Agent when it joins, that gives the Agent new possible Actions and Inputs.
 */
// TODO: allow the joining user to pass in parameters (e.g. preferred nickname in a chat)
trait Activity extends Entity {

  private var sessionIdSequence = new IdSequence()

  private var sessions: Map[ActivitySessionId, ActivitySession] = Map()

  /**
   * Called when an agent tries to join an activity.
   * Returns a session for the activity that should be stored with the agent managed object.
   *
   * Throws an exception if joining was not possible.
   */
  final def join(agent: Agent, agentSessionId: Long, parameters: Parameters): ActivityProxy = {
    if (agent == null) throw new IllegalArgumentException("Trying to add null Agent to " + toString)
    if (!canJoin(agent)) throw new ActivityJoinException("Agent " + agent + " could not join " + toString)

    markForUpdate()

    val id = ActivitySessionId(agentSessionId, sessionIdSequence.nextId())
    val session = createActivitySession(id, parameters, agent)
    val activityProxy = createActivityProxy(id, parameters, session, agent)

    sessions += (id -> session)

    onSessionStarted(session)

    return activityProxy
  }

  /**
   * @return true if the specified agent is allowed to join this activity.
   */
  def canJoin(agent: Agent): Boolean = true

  /**
   * Ends the specified ActivitySession.
   */
  final def leave(sessionId: ActivitySessionId) {
    sessions.get(sessionId) match {
      case Some(session) =>
        markForUpdate()
        onSessionEnded(session)
        sessions -= sessionId

      case None =>
    }
  }

  /**
   * Return the session with the specified id if found.
   */
  def getSession(sessionId: ActivitySessionId): Option[ActivitySession] = sessions.get(sessionId)

  /**
   * Returns an iterator over the sessions currently connected to this activity.
   */
  def getSessions(): Iterator[ActivitySession] = sessions.values

  /**
   * Sends the specified event to all activity proxies of this activity
   */
  def sendMessageToAll(action: Symbol, parameters: Parameters) {
    sessions.values foreach { s: ActivitySession => s.sendMessage(action, parameters) }
  }

  protected def createActivitySession(sessionId: ActivitySessionId, parameters: Parameters, agent: Agent): ActivitySession = new ActivitySession(sessionId, parameters, Ref(agent))

  protected def createActivityProxy(sessionId: ActivitySessionId, parameters: Parameters, session: ActivitySession, agent: Agent): ActivityProxy = new ActivityProxy(sessionId, parameters, Ref(this), Ref(agent))

  protected def onSessionStarted(session: ActivitySession) {}

  protected def onSessionEnded(session: ActivitySession) {}


}

