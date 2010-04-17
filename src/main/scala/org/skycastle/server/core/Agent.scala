package org.skycastle.server

import _root_.com.sun.sgs.app.ManagedObject
import _root_.java.io.Serializable
import activity.ActivitySessionId
import core.MessageHandler
import util.{Parameters, IdSequence}

/**
 * Something that can participate in Activities.
 *
 * Used to represent both player avatars and non-player characters, sometimes also bots, machines, or similar.
 */
trait Agent extends Entity with MessageHandler {

  private var activities: Map[ActivitySessionId, ActivityProxy] = Map()
  private var activityIdSequence = new IdSequence()

  def joinActivity(activity: Activity, parameters: Parameters): ActivitySessionId = {
    if (activity.canJoin(this)) {
      val activityProxy = activity.join(this, activityIdSequence.nextId(), parameters)
      val id = activityProxy.sessionId
      activities += (id -> activityProxy)

      // Notify controller (player / AI) that we joined the activity
      // TODO: Provide the type of the activity, or some other hints that allows the client to arrange a suitable UI for it?
      //       Or instead allow the client to request that, or the activity to send it?
      sendMessage(new Message(id, 'joinedActivity, null))

      id
    }
    else null
  }

  def leaveActivity(id: ActivitySessionId) {
    activities.get(id) match {
      case Some(a:ActivityProxy) =>
        a.activity().leave(id)
        activities -= id

        // Notify controller (player / AI) that we left the activity
        sendMessage(new Message(id, 'leftActivity, null))

      case None =>
    }
  }

  def getActivityProxy(sessionId: ActivitySessionId): Option[ActivityProxy] = activities.get(sessionId)

  override def handleMessage(message: Message): Boolean = {
    // Dispatch message from controller to the correct activity proxy
    getActivityProxy(message.activitySession) match {
      case Some(a: ActivityProxy) => a.handleMessage(message)
      case None => false
    }
  }


}

