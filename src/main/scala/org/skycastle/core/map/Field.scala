package org.skycastle.core.map

/**
 * Numerical field over a terrain.
 */
trait Field {

  def apply(x: Float, y: Float, gridSize: Float): Float

  
  

}