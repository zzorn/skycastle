package org.skycastle.server.util

import _root_.com.sun.sgs.app.ManagedObject
import _root_.java.io.Serializable

object Ref {
  /**
   * Create indirect reference.
   * Hides the actual implementation.
   */
  // TODO: On the client, create direct references for managed objects also.
  def apply[T](obj: T): Ref[T] = {
    obj match {
      case o: ManagedObject => ManagedRef.fromObject(o).asInstanceOf[Ref[T]]
      case o: Ref[T] => o
      case o => DirectRef[T](o)
    }
  }
}

/**
 * An indirect or direct reference to some object.
 */
trait Ref[T] extends Serializable {

  def apply(): T

  def getForUpdate(): T

}

