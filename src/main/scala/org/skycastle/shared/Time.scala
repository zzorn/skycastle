package org.skycastle.shared.data

/**
 * Encapsulates update time, holds both the current time and the time since an update was done,
 * and things like average update rate etc.
 */
trait Time {

  def frame: Double
  def frameRealime: Long

  def totalGameTime: Double

  def totalRealTime: Long


  /**
   * In milliseconds since epoch.
   */
  def currentTime(): Long

  /**
   * In milliseconds.
   */
  def timeSinceLastUpdate(): Long

  /**
   * Calculated update rate.
   */
  def updatesPerSecond(): Float

}
