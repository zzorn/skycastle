package org.jmespike.utils

import simplex3d.math.float.functions._
import simplex3d.math.float._
import simplex3d.math.floatx._

import com.jme3.math.{ColorRGBA, Vector3f, Vector2f}

/**
 * Convert between JME3 and Simplex3D vectors
 */
object VectorConversions {

  implicit def vec2ToVector2(v: Vec2): Vector2f = new Vector2f(v.x, v.y)
  implicit def inVec2ToVector2(v: inVec2): Vector2f = new Vector2f(v.x, v.y)
  implicit def vector2ToVec2(v: Vector2f): Vec2 = Vec2(v.x, v.y)

  implicit def vec3ToVector3(v: Vec3): Vector3f = new Vector3f(v.x, v.y, v.z)
  implicit def inVec3ToVector3(v: inVec3): Vector3f = new Vector3f(v.x, v.y, v.z)
  implicit def vector3ToVec3(v: Vector3f): Vec3 = Vec3(v.x, v.y, v.z)

  implicit def vec4ToColor(v: Vec4): ColorRGBA= new ColorRGBA(v.r, v.g, v.b, v.a)
  implicit def inVec4ToColor(v: inVec4): ColorRGBA= new ColorRGBA(v.r, v.g, v.b, v.a)
  implicit def colorToVec4(v: ColorRGBA): Vec4 = Vec4(v.r, v.g, v.b, v.a)

}