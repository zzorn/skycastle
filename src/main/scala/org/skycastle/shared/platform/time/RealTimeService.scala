package org.skycastle.shared.platform.time


/**
 * Time service that returns the real time.
 */
object RealTimeService extends TimeService {
  def currentGameTime = Time(System.currentTimeMillis)
}

