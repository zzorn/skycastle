package org.skycastle.shared.platform.scheduler

import _root_.com.sun.sgs.app.Task
import _root_.org.skycastle.shared.platform.time.Time

/**
 * A service for scheduling callbacks.
 */
trait SchedulerService {

  def scheduleCallback(time: Time, callback: Task)

}
