package org.skycastle.shared.time

import _root_.org.skycastle.shared.Time

/**
 * Time service that returns the real time.
 */
class RealTimeService extends TimeService {
  def currentGameTime = Time(System.currentTimeMillis)
}

