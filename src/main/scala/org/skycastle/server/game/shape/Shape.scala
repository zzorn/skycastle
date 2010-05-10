package org.skycastle.server.game.components

import _root_.org.skycastle.server.game.Facet
import _root_.org.skycastle.shared.math.Vector3

/**
 * Describes a parametrized shape.
 */
trait Shape {
  def volume: Float = 0
  def longestSide: Float = 0
  def bounds: Vector3 = Vector3.Zero
}

