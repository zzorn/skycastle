package org.skycastle.shared.data

/**
 * Something that can get updated regularly.
 */
trait Updateable {
  def update(time: Time)
}
