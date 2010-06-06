package org.skycastle.core.data

import java.io.Serializable

/**
 * 
 */
trait Value extends Serializable {

  def self: Self
/* TODO: Replace with creating new immutable copy if this is mutable
*/
  def immutable: Self = self


  type Self <: Value
  type T

  def defaultValue: T


/* TODO
  def createCopy(immutable: Boolean): Self

  def immutable: Self
  def isImmutable = false
*/

  def apply(): T
  def := (value: T)

  def value = apply()
  def value_= (v: T) = := (v)

  def replaceParameterReferences(parameters: Data): Value = self
  def invokeFunctions(): Value = self


  def isBoolean: Boolean = false
  def isNumber: Boolean = false
  def isText: Boolean = false
  def isList: Boolean = false
  def isMap: Boolean = false

  def asBoolean: Boolean = error("boolean")
  def asInt: Int = error("number")
  def asLong: Long = error("number")
  def asFloat: Float = error("number")
  def asDouble: Double = error("number")
  def asString: String = error("string")
  def asList: List[Value] = error("list")
  def asMap: Map[Symbol, Value] = error("map")

  def asBoolean(default: Boolean): Boolean = if (isBoolean) asBoolean else default
  def asInt(default: Int): Int = if (isNumber) asInt else default
  def asLong(default: Long): Long = if (isNumber) asLong else default
  def asFloat(default: Float): Float = if (isNumber) asFloat else default
  def asDouble(default: Double): Double = if (isNumber) asDouble else default
  def asString(default: String): String = if (isText) asString else default
  def asList(default: List[Value]): List[Value] = if (isList) asList else default
  def asMap(default: Map[Symbol, Value]): Map[Symbol, Value] = if (isMap) asMap else default

  def asBoolean(name: Symbol, default: Boolean): Boolean = getAs(name, default, _.isBoolean, _.asBoolean )
  def asInt(name: Symbol, default: Int): Int = getAs(name, default, _.isNumber, _.asInt )
  def asLong(name: Symbol, default: Long): Long = getAs(name, default, _.isNumber, _.asLong )
  def asFloat(name: Symbol, default: Float): Float = getAs(name, default, _.isNumber, _.asFloat )
  def asDouble(name: Symbol, default: Double): Double = getAs(name, default, _.isNumber, _.asDouble )
  def asString(name: Symbol, default: String): String = getAs(name, default, _.isText, _.asString )
  def asList(name: Symbol, default: List[Value]): List[Value] = getAs(name, default, _.isList, _.asList )
  def asMap(name: Symbol, default: Map[Symbol, Value]): Map[Symbol, Value] = getAs(name, default, _.isMap, _.asMap)

  def asInt(name: Symbol, default: Int, min: Int): Int = getAsMin(name, default, min, (v: Value) => v.asInt, (a: Int, b:Int) => a < b )
  def asLong(name: Symbol, default: Long, min: Long): Long = getAsMin(name, default, min, (v: Value) => v.asLong, (a: Long, b:Long) => a < b)
  def asFloat(name: Symbol, default: Float, min: Float): Float = getAsMin(name, default, min, (v: Value) => v.asFloat, (a: Float, b:Float) => a < b)
  def asDouble(name: Symbol, default: Double, min: Double): Double = getAsMin(name, default, min, (v: Value) => v.asDouble, (a: Double, b:Double) => a < b)

  def asInt(name: Symbol, default: Int, min: Int, max: Int): Int = getAsMinMax(name, default, min, max, (v: Value) => v.asInt, (a: Int, b:Int) => a < b  )
  def asLong(name: Symbol, default: Long, min: Long, max: Long): Long = getAsMinMax(name, default, min, max, (v: Value) => v.asLong, (a: Long, b:Long) => a < b)
  def asFloat(name: Symbol, default: Float, min: Float, max: Float): Float = getAsMinMax(name, default, min, max, (v: Value) => v.asFloat, (a: Float, b:Float) => a < b)
  def asDouble(name: Symbol, default: Double, min: Double, max: Double): Double = getAsMinMax(name, default, min, max, (v: Value) => v.asDouble, (a: Double, b:Double) => a < b )


  private def getAs[T](name: Symbol, default: T, test: (Value) => Boolean, extractor: (Value) => T): T = {
    if (isMap && asMap.contains(name) && test(asMap.apply(name))) extractor(asMap.apply(name)) else default
  }

  private def getAsMin[T](name: Symbol, default: T, min: T, extractor: (Value) => T, less: (T, T) => Boolean): T = {
    val value = getAs(name, default, _.isNumber, extractor)
    if (less(value, min)) min else value
  }

  private def getAsMinMax[T](name: Symbol, default: T, min: T, max: T, extractor: (Value) => T, less: (T, T) => Boolean): T = {
    val value = getAs(name, default, _.isNumber, extractor)
    if (less(value, min)) min else if (less(value, max)) value else max
  }

  private def error(message: String) = throw new UnsupportedOperationException("Operation not supported, value is not a " + message)

  // Equal if the values are equal
  override def equals(obj: Any): Boolean = {
    if (obj == null || !obj.isInstanceOf[Value]) return false
    else return value == obj.asInstanceOf[Value].value
  }

  override def hashCode = value.hashCode

}