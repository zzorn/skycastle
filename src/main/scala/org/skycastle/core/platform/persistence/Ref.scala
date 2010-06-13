package org.skycastle.core.platform.persistence

import _root_.java.io.Serializable

/**
 * 
 */
trait Ref[T <: Persistent] extends Serializable {
  def apply(): T = get()
  def get(): T
  def getForUpdate(): T
}

