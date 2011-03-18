package org.skycastle.core.space.grid

import _root_.org.skycastle.core.space.Position

/**
 * Holds content of one cell in the grid.
 */
class GridCell {
  var items: List[Position] = Nil

  def blocked = false
  def groundHeight: Float = 0
  def ceilingHeight: Float = 3

}