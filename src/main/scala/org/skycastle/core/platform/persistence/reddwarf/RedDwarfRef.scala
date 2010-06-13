package org.skycastle.core.platform.persistence.reddwarf

import _root_.com.sun.sgs.app.ManagedReference
import _root_.org.skycastle.core.platform.persistence.{Ref, Persistent}

/**
 * 
 */
class RedDwarfRef[T <: Persistent](ref: ManagedReference[T]) extends Ref[T] {
  def get(): T = ref.get
  def getForUpdate(): T = ref.getForUpdate
}

