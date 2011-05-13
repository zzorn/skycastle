package org.jmespike.controls

import com.jme3.scene.Spatial

/**
 * Provides a random seed of the given entity.
 */
object SeedCalculator {

  def randomSeedOf(entity: Spatial) = entity.hashCode

}