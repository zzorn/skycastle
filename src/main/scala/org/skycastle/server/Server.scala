package org.skycastle.server

/**
 * Root object for a server.  Handles creation of some avatar when a player joins,
 * and joining the initial activity.
 */
trait Server {

  def getAgent(name: String): Agent

  // TODO: Replace with a reference to a default Activity instance?
  // TODO: Support activities that don't store references to their members, for things like general server interaction
  def join(agent: Agent): ActivityProxy
}

