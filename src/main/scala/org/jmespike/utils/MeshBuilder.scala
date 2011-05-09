package org.jmespike.utils

import simplex3d.math.float.functions._
import simplex3d.math.float._
import java.util.ArrayList
import simplex3d.math.floatx.functions._
import com.jme3.scene.{VertexBuffer, Mesh}
import com.jme3.util.BufferUtils
import java.nio.{IntBuffer, FloatBuffer}

/**
 * Add vertexes and triangles to it, then create a mesh.
 */
class MeshBuilder(inverted: Boolean = false, twoSided: Boolean = false) {

  private val defaultColor = Vec4(1,1,1,1)

  private val vertexSize = 3
  private val normalSize = 3
  private val texelSize  = 2
  private val colorSize  = 4

  private var indexes:  ArrayList[Int]   = new ArrayList[Int]()
  private var vertices: ArrayList[Float] = new ArrayList[Float]()
  private var texels:   ArrayList[Float] = new ArrayList[Float]()
  private var normals:  ArrayList[Float] = new ArrayList[Float]()
  private var colors:   ArrayList[Float] = new ArrayList[Float]()

  private var index  = 0
  private var vertex = 0

  def vertexCount = vertex
  def indexCount = index

  /**
   * Adds a triangle between the specified vertexes.
   * Specify vertexes in counter clockwise order relative to desired surface direction.
   */
  def addTriangle(a: Int, b: Int, c: Int) {
    if (!inverted || twoSided) {
      indexes.add(c)
      indexes.add(b)
      indexes.add(a)
      index += 3
    }

    if (inverted || twoSided) {
      indexes.add(a)
      indexes.add(b)
      indexes.add(c)
      index += 3
    }
  }

  /**
   * Adds a quad consisting of two triangles.
   */
  def addQuad(a: Int, b: Int, c: Int, d: Int) {
    addTriangle(a, b, c)
    addTriangle(c, d, a)
  }

  /**
   * Adds a new vertex and returns its vertex number, for use when adding triangles.
   */
  def addVertex(pos:    inVec3,
                texel:  inVec2 = Vec2.Zero,
                color:  inVec4 = defaultColor,
                normal: inVec3 = Vec3.UnitY): Int = {

    vertices.add(pos.x)
    vertices.add(pos.y)
    vertices.add(pos.z)

    texels.add(texel.x)
    texels.add(texel.y)

    colors.add(color.r)
    colors.add(color.g)
    colors.add(color.b)
    colors.add(color.a)

    normals.add(normal.x)
    normals.add(normal.y)
    normals.add(normal.z)

    // Increment vertex, and return index of created vertex
    val currentVertex = vertex
    vertex += 1
    currentVertex
  }

  /**
   * Create a jme3 mesh object based on the entered vertex and triangle information.
   * Creates normals based on triangles by default.
   */
  def createMesh(updateNormals: Boolean = true): Mesh = {

    def createFloatBuffer(data: ArrayList[Float]): FloatBuffer = {
      val buff = BufferUtils.createFloatBuffer(data.size)
      var i = 0
      while (i < data.size) {
        buff.put(data.get(i))
        i += 1
      }
      buff.flip()
      buff
    }

    def createIntBuffer(data: ArrayList[Int]): IntBuffer = {
      val buff = BufferUtils.createIntBuffer(data.size)
      var i = 0
      while (i < data.size) {
        buff.put(data.get(i))
        i += 1
      }
      buff.flip()
      buff
    }

    // Update normals based on triangles
    if (updateNormals) buildNormals()

    val mesh = new Mesh()
    mesh.setBuffer(VertexBuffer.Type.Position, 3, createFloatBuffer(vertices))
    mesh.setBuffer(VertexBuffer.Type.Normal,   3, createFloatBuffer(normals))
    mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, createFloatBuffer(texels))
    mesh.setBuffer(VertexBuffer.Type.Color,    4, createFloatBuffer(colors))
    mesh.setBuffer(VertexBuffer.Type.Index,    1, createIntBuffer(indexes))
    mesh
  }


  def vertex(vertexIndex: Int): Vec3 = {
    require(vertexIndex >= 0 || vertexIndex  < vertexCount, "Vertex index was "+vertexIndex+", which is out of bounds (0.."+vertexCount+")")
    Vec3(vertices.get(vertexIndex * vertexSize),
         vertices.get(vertexIndex * vertexSize + 1),
         vertices.get(vertexIndex * vertexSize + 2))
  }

  def texel(vertexIndex: Int): Vec2 = {
    require(vertexIndex >= 0 || vertexIndex  < vertexCount, "Vertex index was "+vertexIndex+", which is out of bounds (0.."+vertexCount+")")
    Vec2(texels.get(vertexIndex * texelSize),
         texels.get(vertexIndex * texelSize + 1))
  }

  def color(vertexIndex: Int): Vec4 = {
    require(vertexIndex >= 0 || vertexIndex  < vertexCount, "Vertex index was "+vertexIndex+", which is out of bounds (0.."+vertexCount+")")
    Vec4(colors.get(vertexIndex * colorSize),
         colors.get(vertexIndex * colorSize + 1),
         colors.get(vertexIndex * colorSize + 2),
         colors.get(vertexIndex * colorSize + 3))
  }

  def normal(vertexIndex: Int): Vec3 = {
    require(vertexIndex >= 0 || vertexIndex  < vertexCount, "Vertex index was "+vertexIndex+", which is out of bounds (0.."+vertexCount+")")
    Vec3(normals.get(vertexIndex * normalSize),
         normals.get(vertexIndex * normalSize + 1),
         normals.get(vertexIndex * normalSize + 2))
  }


  /**
   * Updates the normals based on the triangles.
   */
  def buildNormals() {

    def setNormal(vertexIndex: Int, normal: inVec3) {
      normals.set(vertexIndex * vertexSize    , normal.x)
      normals.set(vertexIndex * vertexSize + 1, normal.y)
      normals.set(vertexIndex * vertexSize + 2, normal.z)
    }

    def addToNormal(vertexIndex: Int, addition: inVec3) {
      setNormal(vertexIndex, normal(vertexIndex) + addition)
    }

    def lengthSquared(v: Vec3): Float = v.x*v.x + v.y*v.y + v.z*v.z

    // Clear all normals
    var clearIndex = 0
    while (clearIndex < vertexCount) {
      setNormal(clearIndex, Vec3.Zero)
      clearIndex += 1
    }


    // Calculate normal for each triangle
    var i = 0
    val sideAB = Vec3(0,0,0)
    val sideAC = Vec3(0,0,0)
    while (i < indexes.size) {
      val ai = indexes.get(i)
      val bi = indexes.get(i + 1)
      val ci = indexes.get(i + 2)
      val a = vertex(ai)
      val b = vertex(bi)
      val c = vertex(ci)

      sideAB := b
      sideAB -= a
      sideAC := c
      sideAC -= a

      val normal = normalize(cross(sideAB, sideAC))

      // Triangles with no surface area do not affect the normals of nearby sides
      if (lengthSquared(normal) > 0) {
        addToNormal(ai, normal)
        addToNormal(bi, normal)
        addToNormal(ci, normal)
      }

      i += 3
    }

    // Normalize all normals
    var normalIndex = 0
    while (normalIndex < vertexCount) {
      setNormal(normalIndex, normalize(normal(normalIndex)))
      normalIndex += 1
    }
  }
}