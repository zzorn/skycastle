package org.skycastle.shared.persistence.memory

import _root_.org.skycastle.shared.entity.Persistent
import _root_.org.skycastle.shared.persistence.{InMemoryPlatformServices, Ref}

/**
 * 
 */
case class MemoryRef[T <: Persistent](value: T) extends Ref[T]{
  def getForUpdate() = value
  def get() = value
}

