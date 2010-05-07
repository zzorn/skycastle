package org.skycastle.server.game.components

import _root_.org.skycastle.server.game.Facet
import _root_.org.skycastle.shared.math.Vector3

/**
 * Describes a parametrized shape.
 */
trait Shape {
  def volume: Double
  def longestSide: Double
  def bounds: Vector3
}

