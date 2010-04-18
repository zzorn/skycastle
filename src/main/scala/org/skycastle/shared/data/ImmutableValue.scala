package org.skycastle.shared.data

import primitives.Path

/**
 * 
 */
trait ImmutableValue extends Value {

  def set(index: Index, value: Value) = throwError("Can not set anything in an immutable value")
  def add(value: Value) = throwError("Can not add anything to an immutable value")

  protected def getValue(index: Index): Option[Value] = None
  protected def removeValue(index: Index) = throwError("Can not remove anything from an immutable value")

  private def throwError(message: String) {
    throw new UnsupportedOperationException(message)
  }
}
