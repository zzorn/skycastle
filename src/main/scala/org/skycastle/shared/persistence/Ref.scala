package org.skycastle.shared.persistence

import _root_.java.io.Serializable
import _root_.org.skycastle.shared.entity.Persistent

/**
 * 
 */
trait Ref[T <: Persistent] extends Serializable {
  def apply() = get()
  def get()
  def getForUpdate()
}

