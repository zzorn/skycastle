package org.skycastle.shared.platform.scheduler

import _root_.com.sun.sgs.app.Task
import _root_.java.io.Serializable
import _root_.org.skycastle.shared.data.Data

/**
 * Serializable object used for callbacks.
 */
class Callback(entity: Taskable, inputParameters: Data) extends Task with Serializable {
  private val parameters: Data = inputParameters.immutable

  def run = entity.callback(parameters)
}

