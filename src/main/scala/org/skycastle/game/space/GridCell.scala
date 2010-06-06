package org.skycastle.game.space

/**
 * Holds content of one cell in the grid.
 */
class GridCell {
  var items: List[Item] = Nil

  def blocked = false
  def groundHeight: Float = 0
  def ceilingHeight: Float = 3

}