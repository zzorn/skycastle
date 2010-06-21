package org.skycastle.view.shape.sgine

import _root_.org.sgine.math.Vector3
import collection.immutable.SortedMap

/**
 * A function from a double value to a vector.
 * Defined by a set of mappings between double values and vectors.
 * Interpolates between adjacent values when calculating the value at a point,
 * or uses the closest value if there is a value only on one side.
 */
case class VectorGradient(vectors: SortedMap[Double, Vector3]) extends Function1[Double, Vector3] {

  // TODO: Add constructor that takes normal map and creates sorted map rom it.

  def apply(t: Double): Vector3 = {
    vectors.get(t) match {
      case Some(vector) => vector
      case None =>
        // Find closest value on both sides
        // TODO

        // If there is a value only on one side, return that
        // TODO

        // If there is a value on both sides, interpolate between them
        // TODO
    }
  }

}

