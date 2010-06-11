package org.skycastle.core.platform.scheduler

import _root_.com.sun.sgs.app.Task
import _root_.java.io.Serializable
import _root_.org.skycastle.core.data.Data

/**
 * Serializable object used for callbacks.
 */
class Callback(entity: Taskable, parameters: Data) extends Task with Serializable {

  def run = entity.callback(parameters)

}

