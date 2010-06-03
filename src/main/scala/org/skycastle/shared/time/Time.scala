package org.skycastle.shared

import _root_.java.io.Serializable

/**
 * Wraps an instant in time, adding some utility functions.
 */
case class Time(ms: Long) extends Serializable {

  def s: Double = ms * 0.001

  
}

