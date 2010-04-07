package org.skycastle.server

import _root_.com.sun.sgs.app.ManagedObject
import _root_.java.io.Serializable

/**
 * Something that can participate in Activities.
 *
 * Used to represent both player avatars and non-player characters, sometimes also bots, machines, or similar.
 */
trait Agent extends Entity with ManagedObject with Serializable {

  private val activities: List[ActivityProxy] = Nil

  /**
   * Called when an agent has left an activity for some reason.
   *
   * TODO: Replace with 'system' event / perception stream from the activity instead.  Or just call from a handler for that?
   */
  def onLeftActivity(activity: Activity)

}

