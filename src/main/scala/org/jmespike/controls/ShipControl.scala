package org.jmespike.controls

import com.jme3.scene.control.AbstractControl
import com.jme3.renderer.{ViewPort, RenderManager}
import com.jme3.scene.Spatial
import simplex3d.math.float.functions._
import simplex3d.math.float._
import org.jmespike.utils.VectorConversions._
import com.jme3.math.{Vector3f, Quaternion}

/**
 * Handles ship physics.
 */
class ShipControl(steeringConf: SteeringConf) extends BaseControl with Steerable {

  def createCopy = new ShipControl(steeringConf)

  var orientation: Quaternion = new Quaternion()

  val velocity: Vec3= new Vec3()

  def controlUpdate(timeDelta: Float) {

    // Update orientation
    val currentOrientation: Quaternion = new Quaternion(spatial.getLocalRotation)
    currentOrientation.slerp(heading, steeringConf.turnSpeed() * timeDelta)
    spatial.setLocalRotation(currentOrientation)

    // Make sure steering vectors are within bounds
    clamp(steering, -1, 1)

    // Convert steering to thrust
    val force = steeringConf.steeringToForce(steering) * timeDelta

    // Direct thrust according to our current orientation
    val directedForce = currentOrientation.mult(force)

    // TODO: Apply gravitation from nearby planets and stars

    // Update velocity
    velocity += directedForce

    // TODO: Apply damping force on velocity (sort of friction), to achieve more arcade style ship control

    // Update pos
    val currentPos: Vec3 = spatial.getLocalTranslation
    currentPos += velocity * timeDelta
    spatial.setLocalTranslation(currentPos)
  }

  private def steeringToForce(steering: Vec3, steeringForce: SteeringConf): Vec3 = {
    val force = Vec3(steering)

    // Forward-back
    if (force.x > 0) force.x *= steeringForce.forward()
    else             force.x *= steeringForce.back()

    // Up-down
    if (force.y > 0) force.y *= steeringForce.up()
    else             force.y *= steeringForce.down()

    // Sideways
    if (force.z > 0) force.y *= steeringForce.left()
    else             force.y *= steeringForce.right()

    force
  }

}