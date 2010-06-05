package org.skycastle.shared.platform.persistence

import _root_.java.io.Serializable

/**
 * 
 */
trait Ref[T <: Persistent] extends Serializable {
  def apply() = get()
  def get()
  def getForUpdate()
}

