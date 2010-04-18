package org.skycastle.shared.data

/**
 * Something that can get updated regularily.
 */
trait Updateable {
  def update(time: Time)
}
