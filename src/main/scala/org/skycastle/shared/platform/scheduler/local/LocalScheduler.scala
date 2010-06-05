package org.skycastle.shared.platform.scheduler.local

import _root_.com.sun.sgs.app.Task
import _root_.java.util.PriorityQueue
import _root_.org.skycastle.shared.platform.SkycastleContext
import _root_.org.skycastle.shared.platform.time.Time
import _root_.org.skycastle.shared.platform.scheduler.SchedulerService

/**
 * 
 */
class LocalScheduler extends SchedulerService {

  private val tasks: PriorityQueue[ScheduledTask] = new PriorityQueue[ScheduledTask]

  def scheduleCallback(time: Time, callback: Task) = tasks add ScheduledTask(time, callback)

  def update() {
    val currentTime = SkycastleContext.timeService.currentGameTime

    while(tasks.peek != null && tasks.peek.time.ms <= currentTime.ms) {
      tasks.poll.callback.run()
    }
  }

  def start() {
    // TODO: Create a worker thread to call update, and change update to invoke the changes in the swing thread??
  }

  private case class ScheduledTask(time: Time, callback: Task) extends Comparable[ScheduledTask] {
    def compareTo(o: ScheduledTask) = if (time.ms < o.time.ms) -1 else if (time.ms > o.time.ms) 1 else 0
  }

}
