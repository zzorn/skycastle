package org.skycastle.view.shape

import java.awt.Color

object Shape {
  def apply() = null // TODO: Instantiate with 3D library specific Shape implementation
}

/**
 * A 3D shape node.
 */
trait Shape {

  // TODO: Create an own simple vector3 class after all?
  def addBox(x: Double = 0, y: Double = 0, z: Double = 0, material: String = null, color: Color = Color.WHITE): Shape


  def volume_m3 = 0 // TODO: Calculate somehow?  At least approximately..  Somewhat of a bitch to do for overlapping objects...
}
