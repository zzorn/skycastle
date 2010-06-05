package org.skycastle.shared.platform.persistence.reddwarf

import _root_.com.sun.sgs.app.ManagedReference
import _root_.org.skycastle.shared.platform.persistence.{Ref, Persistent}

/**
 * 
 */
class RedDwarfRef[T <: Persistent](ref: ManagedReference[T]) extends Ref[T] {
  def get() = ref.get
  def getForUpdate() = ref.getForUpdate
}

