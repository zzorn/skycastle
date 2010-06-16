package org.skycastle.core.shape

import org.skycastle.core.data.Data
import java.awt.Color

object Shape {
  def apply() = null // TODO: Instantiate with 3D library specific Shape implementation
}

/**
 * A 3D shape node.
 */
trait Shape {

  // TODO: Create an own simple vector3 class after all?
  def addBox(x: Double = 0, y: Double = 0, z: Double = 0, material: String = null, color: Color = Color.WHITE)


}
