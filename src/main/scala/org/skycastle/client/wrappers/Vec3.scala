package org.skycastle.client.wrappers

import org.scalaprops.Bean
import com.jme3.math.Vector3f

/**
 * Wraps a 3D vector, allowing it to be read from configuration files.
 */
class Vec3 extends Bean {

  val x = p('x, 0f)
  val y = p('y, 0f)
  val z = p('z, 0f)

  setBeanName('Vec3)

  def this(x: Float, y: Float, z: Float) {
    this()
    this.x := x
    this.y := y
    this.z := z
  }

  def this(vec3: Vector3f) {
    this()
    this.x := vec3.x
    this.y := vec3.y
    this.z := vec3.z
  }

  def toVector3f: Vector3f = new Vector3f(x(), y(), z())
}