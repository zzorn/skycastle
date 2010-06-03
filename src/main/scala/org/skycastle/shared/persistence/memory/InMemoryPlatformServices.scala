package org.skycastle.shared.persistence

import _root_.com.sun.sgs.app.Task
import _root_.java.lang.String
import _root_.java.util.{PriorityQueue, HashSet, ArrayList, HashMap}
import _root_.org.skycastle.shared.entity.Persistent
import _root_.org.skycastle.shared.Time
import memory.MemoryRef

/**
 * 
 */

object InMemoryPlatformServices extends PlatformServices {

  private val objects: HashSet[Persistent] = new HashSet()
  private val namedObjects: HashMap[String, Persistent] = new HashMap()
  private val tasks: PriorityQueue[ScheduledTask] = new PriorityQueue[ScheduledTask]

  def createReference[T <: Persistent](obj: T) = new MemoryRef(obj)
  def markForUpdate(obj: Persistent) = {}
  def store(obj: Persistent) = objects.add(obj)
  def delete(obj: Persistent) = objects.remove(obj)

  def get(name: String) = namedObjects.get(name)
  def getForUpdate(name: String) = namedObjects.get(name)
  def bind(name: String, obj: Persistent) = namedObjects.put(name, obj)
  def removeBinding(name: String) = namedObjects.remove(name)

  def scheduleCallback(time: Time, callback: Task) = tasks add ScheduledTask(time, callback)

  def update() {
    val currentTime = SkycastleContext.timeService.currentGameTime

    while(tasks.peek != null && tasks.peek.time.ms <= currentTime.ms) {
      tasks.poll.callback.run()
    }
  }

  private case class ScheduledTask(time: Time, callback: Task) extends Comparable[ScheduledTask] {
    def compareTo(o: ScheduledTask) = if (time.ms < o.time.ms) -1 else if (time.ms > o.time.ms) 1 else 0
  }
}

