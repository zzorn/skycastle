package org.skycastle.core.design

import org.skycastle.util.parameters.Parameters
import org.skycastle.util.parameters.expressions.Expr
import com.jme3.math.Vector3f
import org.skycastle.core.space.Item
import org.skycastle.core.entity.{NoEntity, Entity}

/**
 * Represents a design for some in-game construction.
 */
trait Design {

  var defaultParameters: Parameters = Parameters()

  var expressions: Map[Symbol, Expr] = Map()

  def calculateParameters(context: Parameters): Parameters = context.chain(defaultParameters).remap(expressions)

  // Exponent for the grid scale.  Grid cell size for the component is calculated as 1m * 2^exponent.
  def gridExponent = 0
  final def gridCellSize: Float = math.pow(2, gridExponent).toFloat

  // Size in grid cells (outer size along x, y, z axis in grid cells)
  def gridBounds: (Int, Int, Int) = (1,1,1)
  final def bounds: (Float, Float, Float) = {
    val grids = gridBounds
    val gridSize = gridCellSize
    ( gridSize * grids._1,
      gridSize * grids._2,
      gridSize * grids._3)
  }

  /**
   * Create the construct that this design represents.
   */
  final def create(context: Parameters):  Entity = {
    val parameters = calculateParameters(context)
    val entity = build(parameters)
    initializeEntity(entity, parameters)
    entity
  }

  protected def build(parameters: Parameters):  Entity

  private final def initializeEntity(entity: Entity, context: Parameters) {
    if (entity != NoEntity) {
      entity.addFacet(new Item(new Vector3f(
        context.getFloat('x, 0f),
        context.getFloat('y, 0f),
        context.getFloat('z, 0f))))
    }
  }

}