package org.skycastle.shared.platform.time

import scala.math._

/**
 * Provides current game time and real time.
 */
trait TimeService {
  def currentGameTime: Time

  def delayTo(time: Time): Long = max(0, time.ms - currentGameTime.ms)
}

