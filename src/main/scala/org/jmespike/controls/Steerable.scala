package org.jmespike.controls
import simplex3d.math.float.functions._
import simplex3d.math.float._
import com.jme3.math.Quaternion

/**
 * For vehicles and characters.
 * Each control vector can be set from -1 to 1.  The heading vectors should have a normalized direction.
 */
// TODO: Some vehicles (creatures) turn towards heading automatically, others not..
trait Steerable {

  // Update with camera direction
  var heading: Quaternion = new Quaternion()

  // Steering vector, facing heading along x axis with y up and z to the right(?)
  // Should be assigned current forward / back and strafe speeds
  val steering: Vec3 = Vec3(1,0,0)

  // Turning vector, relative to current direction.  Each component specifies turn speed around specified axis.
  // Yaw pitch roll?
  val turning: Vec3 = Vec3(1,0,0)

  // TODO: How to handle jumps, boosts, etc?


}