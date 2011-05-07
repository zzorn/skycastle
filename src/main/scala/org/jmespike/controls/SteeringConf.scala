package org.jmespike.controls

import org.jmespike.conf.Conf
import simplex3d.math.float.functions._
import simplex3d.math.float._

/**
 * Configuration for ship etc steering.
 */
class SteeringConf extends Conf {

  val forward = p('forward, 10f)
  val back    = p('back, 4f)
  val right   = p('right, 3f)
  val left    = p('left, 3f)
  val up      = p('back, 7f)
  val down    = p('back, 3f)

  val turnSpeed = p('turnSpeed, 0.5f)

  def steeringToForce(steering: inVec3): Vec3 = {
    val force = Vec3(steering)

    // Forward-back
    if (force.x > 0) force.x *= forward()
    else             force.x *= back()

    // Up-down
    if (force.y > 0) force.y *= up()
    else             force.y *= down()

    // Sideways
    if (force.z > 0) force.y *= left()
    else             force.y *= right()

    force
  }

}