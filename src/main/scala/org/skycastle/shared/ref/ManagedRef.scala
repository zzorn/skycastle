package org.skycastle.server.util

import _root_.com.sun.sgs.app.{AppContext, ManagedReference, ManagedObject}

object ManagedRef {
  def fromObject[S <: ManagedObject](obj: S): ManagedRef[S] = {
    new ManagedRef[S](AppContext.getDataManager.createReference(obj))
  }
}

/**
 * Wraps a reference to a manged object.
 */
final case class ManagedRef[T](reference: ManagedReference[T]) extends Ref[T] {

  def apply() = reference.get

  def getForUpdate() = reference.getForUpdate

  override def equals(other: Any): Boolean = {
    other match {
      case null => false
      case o: ManagedRef[_] => reference.equals(o.reference)
      case _ => false
    }
  }
}

