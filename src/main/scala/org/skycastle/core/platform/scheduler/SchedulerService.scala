package org.skycastle.core.platform.scheduler

import _root_.org.skycastle.core.platform.time.Time

/**
 * A service for scheduling callbacks.
 */
trait SchedulerService {

  def scheduleCallback(time: Time, callback: () => Unit)

}
