package org.skycastle.shared.platform.scheduler

import _root_.org.skycastle.shared.data.Data
import _root_.org.skycastle.shared.platform.SkycastleContext
import _root_.org.skycastle.shared.platform.time.Time

/**
 * 
 */
trait Taskable {

  def scheduleCallback(time: Time, parameters: Data) = schedulerService.scheduleCallback(time, new Callback(this, parameters))

  private def schedulerService: SchedulerService = SkycastleContext.schedulerService

  /**
   * Method that gets called when a callback event occurs.
   */
  def callback(parameters: Data) {}

}
