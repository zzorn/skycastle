package org.skycastle.shared.time

import _root_.org.skycastle.shared.Time

/**
 * Provides current game time and real time.
 */
trait TimeService {
  def currentGameTime: Time

  def delayTo(time: Time): Long = Math.max(0, time.ms - currentGameTime.ms)
}

