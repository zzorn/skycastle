package org.skycastle.util.mesh

import com.jme3.util.BufferUtils
import com.jme3.math.{Vector2f, Vector3f}
import com.jme3.scene.{VertexBuffer, Mesh}
import org.skycastle.util.MathUtils._

/**
 * Utility class for building custom JME meshes in the shape of a manipulated cylinder.
 */
object LatheBuilder {

  def createMesh(segments: List[Segment],
                 sides: Int = 12,
                 solidStart: Boolean = true,
                 solidEnd: Boolean = true,
                 doubleSided: Boolean = false,
                 invert: Boolean = false): Mesh = {
    // OPTIMIZE: Use triangle strips for segments and triangle fans for the ends (mesh.setMode())

    val numVertices = (sides + 1) * segments.length +
                      (if (solidStart) 1 else 0) +
                      (if (solidEnd) 1 else 0)
    val numIndexes = ((sides + 1) * segments.length * 2 * 3 +
                      (if (solidStart) (sides + 1) * 3 else 0) +
                      (if (solidEnd) (sides + 1) * 3 else 0)) *
                     (if (doubleSided) 2 else 1)

    val vertices: Array[Vector3f] = new Array[Vector3f](numVertices)
    val texCoords: Array[Vector2f] = new Array[Vector2f](numVertices)
    val indexes: Array[Int] = new Array[Int](numIndexes)

    var vertex = 0
    var index = 0

    def addTriangle(a: Int, b: Int, c: Int) {
      if (invert || doubleSided) {
        indexes(index) = a
        indexes(index + 1) = b
        indexes(index + 2) = c
        index += 3
      }

      if (!invert || doubleSided) {
        indexes(index) = c
        indexes(index + 1) = b
        indexes(index + 2) = a
        index += 3
      }
    }

    def addQuad(a: Int, b: Int, c: Int, d: Int) {
      addTriangle(a, b, c)
      addTriangle(c, d, a)
    }

    def addDisk(center: Int, edgeStart: Int, invert: Boolean = false) {
      var e = edgeStart
      while (e < edgeStart + sides) {
        if (invert) addTriangle(center, e, e + 1)
        else addTriangle(center, e + 1, e)
        e += 1
      }
      if (invert) addTriangle(center, edgeStart + sides, edgeStart)
      else addTriangle(center, edgeStart, edgeStart + sides)
    }

    if (solidStart && !segments.isEmpty) {
      addDisk(0, 1)

      vertices(vertex) = segments.head.centerPos
      texCoords(vertex) = segments.head.centerTexturePos
      vertex += 1
    }

    var firstSegment = true
    segments foreach {segment: Segment =>
      var side = 0
      while (side <= sides) {
        val theta = Tauf * side / sides
        vertices(vertex) = segment.pos(theta)
        texCoords(vertex) = segment.texturePos(theta)

        if (!firstSegment) {
          if (side == 0) {
            addQuad(vertex, vertex + sides, vertex - 1, vertex - sides - 1)
          }
          else {
            addQuad(vertex, vertex - 1, vertex - sides - 2, vertex - sides - 1)
          }
        }

        vertex += 1
        side += 1
      }
      firstSegment= false
    }

    if (solidEnd && !segments.isEmpty) {
      addDisk(vertex, vertex - sides - 1, true)

      vertices(vertex) = segments.last.centerPos
      texCoords(vertex) = segments.last.centerTexturePos
      vertex += 1
    }

    val mesh = new Mesh()
    mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices : _*))
    mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords : _*))
    mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(indexes : _*))
    mesh
  }
  


}