package org.skycastle.server

import _root_.com.sun.sgs.app.ManagedObject
import _root_.java.io.Serializable

/**
 * Something that Agents can join.
 *
 * Can be used for game modes, lobbies, vehicles, minigames, world places (outdoor/room), admin controls, and so on.
 *
 * Provides a local proxy for the Agent when it joins, that gives the Agent new possible Actions and Inputs.
 */
trait Activity extends Entity with ManagedObject with Serializable {

  /**
   * Called when an agent tries to join an activity.
   * Returns a proxy for the activity that can be stored with the agent managed object.
   * If joining is not possible for some reason, None is returned.
   *
   * CHECK: Should we throw an exception instead if joining failed, and provide some 'check if can join' method or similar?
   */
  def join(agent: Agent): Option[ActivityProxy]

  /**
   * Called when an agent is leaving an activity for some reason.
   */
  def leave(agent: Agent)
  
}

