package org.skycastle.shared.math

import java.io.Serializable


/**
 * Vector2 class adapted from sgine library.
 */
object Vector2 {
	def apply(x: Double, y: Double) = {
		val v = new Vector2()
		v.x = x
		v.y = y

		v
	}

	def apply(tuple2 : (Double,Double) ) : Vector2 = Vector2(tuple2._1, tuple2._2)

	/**
	 * A vector with coordinates (0,0)
	 */
	val Zero = Vector2(0, 0)

	/**
	 * A vector with coordinates (0,0)
	 */
	val Origo = Zero

	/**
	 * A unit vector in the direction of the x axis.	Coordinates (1,0)
	 */
	val UnitX = Vector2(1, 0)

	/**
	 * A unit vector in the direction of the y axis.	Coordinates (0,1)
	 */
	val UnitY = Vector2(0, 1)

	/**
	 * A vector with coordinates (1,1)
	 */
	// TODO: Is there some more correct name for this?
	val Ones = Vector2(1, 1)
}


/**
 * A vector with three double values.
 */
class Vector2 protected()  extends Serializable {
	protected var _x: Double = 0.0
	protected var _y: Double = 0.0

	def x = _x
	protected def x_=(_x: Double) = this._x = _x
	def y = _y
	protected def y_=(_y: Double) = this._y = _y

	def +(other: Vector2 ): Vector2 = Vector2(x + other.x, y + other.y)
	def -(other: Vector2 ): Vector2 = Vector2(x - other.x, y - other.y)
	def *(scalar: Double ): Vector2 = Vector2(x * scalar, y * scalar)
	def /(scalar: Double ): Vector2 = Vector2(x / scalar, y / scalar)
	def +(ox: Double, oy: Double): Vector2 = Vector2(x + ox, y + oy)
	def -(ox: Double, oy: Double): Vector2 = Vector2(x - ox, y - oy)

	def unary_- : Vector2 = Vector2(-x, -y)

	/**
	 *	Calculates the length of the vector.
	 */
	def length: Double = Math.sqrt(x * x + y * y )

	/**
	 * Calculates the squared length of the vector.
	 * <p/>
	 * Faster than length, as it doesn't need to use a square root operation.
	 * <p/>
	 * Useful for quickly comparing the relative length of vectors.
	 */
	def lengthSquared: Double = x * x + y * y

	/**
	 * The distance to the position specified by the other vector from the position specified by this vector.
	 */
	def distance( other : Vector2 ) : Double = Math.sqrt( distanceSquared( other ) )

	/**
	 * The squared distance to the position specified by the other vector from the position specified by this vector.
	 * <p/>
	 * Faster than distance, as it doesn't need to use a square root operation.
	 * <p/>
	 * Useful for quickly comparing the relative distances between points.
	 */
	def distanceSquared( other : Vector2 ) : Double = {
		val dx = x - other.x
		val dy = y - other.y
		dx*dx + dy*dy
	}

	/**
	 * Calculates and returns the normalized version of this vector.
	 * A normalized vector is in the same direction as the original vector, but has a length of one.
	 * The normalized vector for (0,0) is (0,0).
	 */
	def normalized : Vector2 = {
		if (isZero) {
			Vector2.Zero
		} else {
			val len = 1.0 / length
			Vector2(x * len, y * len)
		}
	}

	/**
	 * True if this a vector with zero length (that is, x, and y are all 0).
	 */
	def isZero : Boolean = x == 0 && y == 0

	/**
	 * Calculates and returns the dot product of this and the specified vector.
	 * <p/>
	 * The dot product of vectors a and b is defined as a.x*b.x + a.y*b.y
	 */
	def * ( other : Vector2 ) : Double = x * other.x + y * other.y

	/**
	 * Returns a two element list with the x, and y coordinates of the Vector2 in that order.
	 */
	def toList: List[Double] = List(x, y)

	/**
	 * Returns a tuple with the x and y coordinates of the Vector2 in that order.
	 */
	def toTuple : (Double, Double) = (x, y)

	override def toString = "Vector2("+x+", "+y+")"

	override def equals(o: Any): Boolean = {
		o match {
			case v: Vector2 => v.x == x && v.y == y
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
