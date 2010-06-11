package org.skycastle.core.space

import _root_.org.skycastle.core.data.{Num, Data}

/**
 * A space where objects are placed on grids.
 */
class GridSpace extends Space {

  private var grid: Array[GridCell] = null
  private var xSize: Int = 0
  private var ySize: Int = 0

  private val MAX_SIZE = 1000

  override protected def init(parameters: Data) {
    xSize = parameters.get('xSize, Num(100)).clamp(1, MAX_SIZE).toInt
    ySize = parameters.get('ySize, Num(100)).clamp(1, MAX_SIZE).toInt

    grid = new Array[GridCell](xSize * ySize)
  }

  
}
