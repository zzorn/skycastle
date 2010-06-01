package org.skycastle.shared.persistence.reddwarf

import _root_.com.sun.sgs.app.ManagedReference
import _root_.org.skycastle.shared.entity.Persistent
import _root_.org.skycastle.shared.persistence.Ref

/**
 * 
 */
class RedDwarfRef[T <: Persistent](ref: ManagedReference[T]) extends Ref[T] {
  def get() = ref.get
  def getForUpdate() = ref.getForUpdate
}

