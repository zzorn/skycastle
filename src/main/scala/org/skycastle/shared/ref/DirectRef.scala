package org.skycastle.server.util

/**
 * Holds a direct reference to an object.
 */
final case class DirectRef[T](obj: T) extends Ref[T] {

  def apply() = obj

  def getForUpdate() = obj

  override def equals(other: Any): Boolean = {
    other match {
      case null => false
      case o: DirectRef[_] => if (obj == null) o.obj == null else obj.equals(o.obj)
      case _ => false
    }
  }
}

