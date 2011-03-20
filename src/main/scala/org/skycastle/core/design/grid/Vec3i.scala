package org.skycastle.core.design.grid

object Vec3i {
  val ZEROES = Vec3i(0,0,0)
  val ONES = Vec3i(1,1,1)
  val XUNIT = Vec3i(1,0,0)
  val YUNIT = Vec3i(0,1,0)
  val ZUNIT = Vec3i(0,0,1)
}

/**
 * Vector type for 3D integer vectors.
 */
case class Vec3i(x: Int, y: Int, z: Int) {

  def + (other: Vec3i): Vec3i = Vec3i(x + other.x,
                                      y + other.y,
                                      z + other.z)

  def - (other: Vec3i): Vec3i = Vec3i(x - other.x,
                                      y - other.y,
                                      z - other.z)

  def * (i: Int): Vec3i = Vec3i(x * i,
                                     y * i,
                                     z * i)

  def / (i: Int): Vec3i = Vec3i(x / i,
                                y / i,
                                z / i)

  override def toString: String = "(" + x + ", " + y + ", " + z + ")"

}