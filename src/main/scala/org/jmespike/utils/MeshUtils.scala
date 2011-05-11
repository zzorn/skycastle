package org.jmespike.utils

import simplex3d.math.float.functions._
import simplex3d.math.float._
import simplex3d.math.floatx.Vec3f._
import simplex3d.math.floatx.functions._
import org.jmespike.shape.ships.ComponentBase

/**
 * 
 */
object MeshUtils {

  def triangleNormal(a: inVec3, b: inVec3, c: inVec3): Vec3 = {
    val sideAB = b - a
    val sideAC = c - a
    normalize(cross(sideAB, sideAC))
  }

  /**
   * Quaternion representing the specified looking direction with given up direction.
   */
  def lookAtQuat(direction: inVec3, up: inVec3): Quat4 = quaternion(lookAt(direction, up))



}