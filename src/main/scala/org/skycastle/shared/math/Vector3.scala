package org.skycastle.shared.math

import java.io.Serializable

/**
 * Vector3 class borrowed from sgine library.
 */

object Vector3 {
  
	def apply(): Vector3 = Zero

	def apply(x: Double, y: Double, z: Double): Vector3 = {
		val v = new Vector3()
		v.x = x
		v.y = y
		v.z = z

		v
	}

	def apply(tuple3 : (Double,Double,Double) ): Vector3 = Vector3(tuple3._1, tuple3._2, tuple3._3)

	/**
	 * A vector with coordinates (0,0,0)
	 */
	val Zero = Vector3(0, 0, 0)

	/**
	 * A vector with coordinates (0,0,0)
	 */
	val Origo = Zero

	/**
	 * A unit vector in the direction of the x axis.	Coordinates (1,0,0)
	 */
	val UnitX = Vector3(1, 0, 0)

	/**
	 * A unit vector in the direction of the y axis.	Coordinates (0,1,0)
	 */
	val UnitY = Vector3(0, 1, 0)

	/**
	 * A unit vector in the direction of the z axis.	Coordinates (0,0,1)
	 */
	val UnitZ = Vector3(0, 0, 1)

	/**
	 * A vector with coordinates (1,1,1)
	 */
	// TODO: Is there some more correct name for this?
	val Ones = Vector3(1, 1, 1)
}


/**
 * An immutable vector with three double values.
 * <p/>
 * Calculation operations will return a result Vector3 instead of modifying this one.
 */
class Vector3 protected()  extends Serializable{
	protected var _x: Double = 0.0
	protected var _y: Double = 0.0
	protected var _z: Double = 0.0

	def x = _x
	protected def x_=(_x: Double) = this._x = _x
	def y = _y
	protected def y_=(_y: Double) = this._y = _y
	def z = _z
	protected def z_=(_z: Double) = this._z = _z

	def +(other: Vector3 ): Vector3 = Vector3(x + other.x, y + other.y, z + other.z)
	def -(other: Vector3 ): Vector3 = Vector3(x - other.x, y - other.y, z - other.z)
	def *(scalar: Double ): Vector3 = Vector3(x * scalar, y * scalar, z * scalar)
	def /(scalar: Double ): Vector3 = Vector3(x / scalar, y / scalar, z / scalar)
	def +(ox: Double, oy: Double, oz: Double): Vector3 = Vector3(x + ox, y + oy, z + oz)
	def -(ox: Double, oy: Double, oz: Double): Vector3 = Vector3(x - ox, y - oy, z - oz)

	def unary_- : Vector3 = Vector3(-x, -y, -z)

	/**
	 *	Calculates the length of the vector.
	 */
	def length: Double = Math.sqrt(x * x + y * y + z * z)

	/**
	 * Calculates the squared length of the vector.
	 * <p/>
	 * Faster than length, as it doesn't need to use a square root operation.
	 * <p/>
	 * Useful for quickly comparing the relative length of vectors.
	 */
	def lengthSquared: Double = x * x + y * y + z * z

	/**
	 * The distance to the position specified by the other vector from the position specified by this vector.
	 */
	def distance( other : Vector3 ) : Double = Math.sqrt( distanceSquared( other ) )

	/**
	 * The squared distance to the position specified by the other vector from the position specified by this vector.
	 * <p/>
	 * Faster than distance, as it doesn't need to use a square root operation.
	 * <p/>
	 * Useful for quickly comparing the relative distances between points.
	 */
	def distanceSquared( other : Vector3 ) : Double = {
		val dx = x - other.x
		val dy = y - other.y
		val dz = z - other.z
		dx*dx + dy*dy + dz*dz
	}

	/**
	 * Calculates and returns the normalized version of this vector.
	 * A normalized vector is in the same direction as the original vector, but has a length of one.
	 * The normalized vector for (0,0,0) is (0,0,0).
	 */
	def normalized : Vector3 = {
		if (isZero) {
			Vector3.Zero
		} else {
			val len = 1.0 / length
			Vector3(x * len, y * len, z * len)
		}
	}

	/**
	 * True if this a vector with zero length (that is, x, y, and z are all 0).
	 */
	def isZero : Boolean = x == 0 && y == 0 && z == 0

	/**
	 * Calculates and returns the dot product of this and the specified vector.
	 * <p/>
	 * The dot product of vectors a and b is defined as a.x*b.x + a.y*b.y + a.z*b.z
	 */
	def * ( other : Vector3 ) : Double = x * other.x + y * other.y + z * other.z

	/**
	 * Calculates and returns the cross product of this and the specified vector.
	 * <p/>The cross product of vectors a and b is defined as
	 * a X b = (a.y * b.z − a.z * b.y, a.z * b.x − a.x * b.z, a.x * b.y − a.y * b.x).
	 */
	def cross ( o : Vector3 ) : Vector3 = Vector3(y * o.z - z * o.y, z * o.x - x * o.z, x * o.y - y * o.x)

	/**
	 * Returns a three element list with the x, y, and z coordinates of the Vector3 in that order.
	 */
	def toList: List[Double] = List(x, y, z)

	/**
	 * Returns a tuple with the x, y, and z coordinates of the Vector3 in that order.
	 */
	def toTuple : (Double, Double, Double) = (x, y, z)

	override def toString = "Vector3("+x+", "+y+", "+z+")"

	override def equals(o: Any): Boolean = {
		o match {
			case v: Vector3 => v.x == x && v.y == y && v.z == z
			case _ => false
		}
	}


	// TODO: Other useful methods:
	//
	// interpolate (linear, maybe cosine also?)
	// scale add (implemented sensibly for the case where e.g. velocity multiplied with a time step is added to a position)
	// scaling each of the three coordinates ( * (x,y,z) operator?	Risk for confusing with dot product?	Maybe call it scale?)
	// various direction related manipulations?	They might be better for a Quaternion / Direction class thou.
	// polar coordinate conversion (to/from)


}
