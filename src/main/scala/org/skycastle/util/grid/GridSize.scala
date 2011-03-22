package org.skycastle.util.grid

/**
 * 
 */
// TODO: Most used instances can be stored in lookup table
case class GridSize(gridSizeExponent: Int) extends Ordered[GridSize] {
  def gridSizeMeters = math.pow(2.0, gridSizeExponent)

  def largerSize: GridSize = GridSize(gridSizeExponent + 1)
  def smallerSize: GridSize = GridSize(gridSizeExponent - 1)

  def compare(that: GridSize) = this.gridSizeExponent - that.gridSizeExponent

}