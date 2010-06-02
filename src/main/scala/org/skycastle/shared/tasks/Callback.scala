package org.skycastle.shared.tasks

import _root_.com.sun.sgs.app.Task
import _root_.java.io.Serializable
import _root_.org.skycastle.shared.entity.Persistent
import _root_.org.skycastle.shared.model.Data

/**
 * Serializable object used for callbacks.
 */
class Callback(entity: Persistent, inputParameters: Data) extends Task with Serializable {
  private val parameters: Data = inputParameters.immutable

  def run = entity.callback(parameters)
}

