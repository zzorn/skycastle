package org.skycastle.server

/**
 * Root object for a server.  Handles creation of some avatar when a player joins,
 * and joining the initial activity.
 */
trait Server {

  def getAgent(name: String): Agent

  // TODO: Replace with a reference to an Activity instance?
  def join(agent: Agent): ActivityProxy
}

