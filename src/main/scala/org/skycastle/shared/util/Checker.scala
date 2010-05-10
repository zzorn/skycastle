package org.skycastle.shared.util

/**
 * 
 */
object Checker {

  def addIfNotNull[T <: AnyRef](list: List[T], e: T, elementDescription: String): List[T] = {
    if (e == null) throw new IllegalArgumentException(elementDescription + " should not be null but it was.");
    list ::: List(e)
  }

  def addIfNotNullAndNotContained[T <: AnyRef](list: List[T], e: T, elementDescription: String): List[T] = {
    if (e == null) throw new IllegalArgumentException(elementDescription + " should not be null but it was.");
    if (list.contains(e)) throw new IllegalArgumentException(elementDescription + " should not already exist but it did.");
    list ::: List(e)
  }

}

