package org.skycastle.core.platform.scheduler

import _root_.org.skycastle.core.platform.SkycastleContext
import _root_.org.skycastle.core.platform.time.Time

/**
 * 
 */
trait Taskable {

  def scheduleCallback(time: Time, parameters: Map[Symbol, Any]) = schedulerService.scheduleCallback(time, {() => callback(parameters)})

  private def schedulerService: SchedulerService = SkycastleContext.schedulerService

  /**
   * Method that gets called when a callback event occurs.
   */
  def callback(parameters: Map[Symbol, Any]) {}

}
