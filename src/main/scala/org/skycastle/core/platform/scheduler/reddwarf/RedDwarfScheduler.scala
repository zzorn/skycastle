package org.skycastle.core.platform.scheduler.reddwarf

import _root_.com.sun.sgs.app.{Task, AppContext}
import _root_.org.skycastle.core.platform.time.Time
import _root_.org.skycastle.core.platform.scheduler.SchedulerService
import _root_.org.skycastle.core.platform.SkycastleContext

/**
 * 
 */
object RedDwarfScheduler extends SchedulerService {

  def scheduleCallback(time: Time, callback: Task) = taskManager.scheduleTask(callback, SkycastleContext.timeService.delayTo(time) )

  private def taskManager = AppContext.getTaskManager

}
