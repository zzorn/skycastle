package org.skycastle.shared.platform.persistence.memory

import _root_.org.skycastle.shared.platform.persistence.{Ref, Persistent}

/**
 * 
 */
case class MemoryRef[T <: Persistent](value: T) extends Ref[T]{
  def getForUpdate() = value
  def get() = value
}

