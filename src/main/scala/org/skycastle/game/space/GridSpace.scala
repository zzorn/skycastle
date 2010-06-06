package org.skycastle.game.space

import org.skycastle.shared.data.Data


/**
 * A space where objects are placed on grids.
 */
class GridSpace extends Space {

  private var grid: Array[GridCell] = null
  private var xSize: Int = 0
  private var ySize: Int = 0

  private val MAX_SIZE: Int = 1000

  protected def init(parameters: Data) {
    xSize = parameters.asInt('xSize, 100, 1, MAX_SIZE)
    ySize = parameters.asInt('ySize, 100, 1, MAX_SIZE)

    grid = new Array[GridCell](xSize * ySize)
  }

  
}
