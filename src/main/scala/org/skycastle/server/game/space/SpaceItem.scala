package org.skycastle.server.game.space

import _root_.org.skycastle.server.game.Facet
import _root_.org.skycastle.shared.data.Time
import _root_.org.skycastle.shared.math.Vector3

/**
 * Some entity located in a space
 */
class SpaceItem extends Facet {
  var pos: Vector3 = Vector3.Zero
  var velocity: Vector3 = Vector3.Zero
  var acceleration: Vector3 = Vector3.Zero

  // todo: rotation
  
  def update(time: Time) {
    // List of most types of possible movement effects.  Use to split this up in suitable coverrideable components.
    
    // TODO: Get environment forces (gravitation, wind, etc)
    // TODO: Get environment friction (air, surface)
    // TODO: Get environment surface contacts / locations & normals
    // TODO: Calculate force produced by propulsion system (traction with ground or rocket etc, or something else)

    // TODO: Handle rotation

    velocity += acceleration * time.frame
    pos      += velocity     * time.frame

    // TODO: Check for collision with surfaces and other collidable objects, and adjust position accordingly.

    // TODO: Advanced surface force modelling for airplane shapes or aquatic environments

    // TODO: Buyancy in surrounding medium

    // TODO: Inertia, momentum, gyroscopic effects, mass balance effects relative to points of thrust, etc

    // TODO: Biological / robotical shape adaption systems (gait, balance, etc)
  }

}

