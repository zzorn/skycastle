package org.skycastle.util.mesh

import com.jme3.util.BufferUtils
import com.jme3.math.{Vector2f, Vector3f}
import com.jme3.scene.{VertexBuffer, Mesh}

/**
 * Utility class for building custom JME meshes in the shape of a manipulated cylinder.
 */
class LatheBuilder(var sides: Int = 12,
                   var solidStart: Boolean = true,
                   var solidEnd: Boolean = true,
                   var doubleSided: Boolean = false,
                   var invert: Boolean = false) {

  var segments: List[Segment] = Nil

  def addSegment(segment: Segment) = segments = segment :: segments

  private val tau = math.Pi * 2 // See tauday.org

  def createMesh(): Mesh = {
    // OPTIMIZE: Use triangle strips for segments and triangle fans for the ends (mesh.setMode())

    val numVertices = sides * segments.length +
                      (if (solidStart) 1 else 0) +
                      (if (solidEnd) 1 else 0)
    val numIndexes = sides * segments.length * 2 * 3 +
                      (if (solidStart) sides * 3 else 0) +
                      (if (solidEnd) sides * 3 else 0)

    val vertices: Array[Vector3f] = new Array[Vector3f](numVertices)
    val texCoords: Array[Vector2f] = new Array[Vector2f](numVertices)
    val indexes: Array[Int] = new Array[Int](numIndexes)

    // TODO: Test this, it's kind of messy

    var vertex = 0
    var index = 0

    def addTriangle(a: Int, b: Int, c: Int) {
      indexes(index) = a
      indexes(index + 1) = b
      indexes(index + 2) = c
      index += 3
    }

    if (solidStart && !segments.isEmpty) {
      vertices(vertex) = segments.head.centerPos
      texCoords(vertex) = segments.head.centerTexturePos
      vertex += 1

      var side = 0
      while (side < sides - 1) {
        addTriangle(0, vertex + side, vertex + side + 1)
        side += 1
      }
      addTriangle(0, vertex + sides - 1, vertex)
    }

    var firstSegment = true
    segments foreach {segment: Segment =>
      var side = 0
      while (side < sides) {
        val theta = tau * side / sides
        vertices(vertex) = segment.pos(theta)
        texCoords(vertex) = segment.texturePos(theta)
        vertex += 1
        side += 1

        if (!firstSegment) {
          if (side < sides - 1) {
            addTriangle(vertex - sides, vertex, vertex + 1)
            addTriangle(vertex + 1, vertex - sides + 1, vertex - sides)
          }
          else {
            addTriangle(vertex - sides, vertex, vertex + 1 - sides)
            addTriangle(vertex + 1 - sides, vertex - sides + 1 - sides, vertex - sides)
          }
        }
      }
      firstSegment= false
    }

    if (solidEnd && !segments.isEmpty) {
      var side = 0
      while (side < sides - 1) {
        addTriangle(vertex, vertex - sides + side, vertex - sides + side + 1)
        side += 1
      }
      addTriangle(vertex, vertex - 1, vertex - sides - 1)

      vertices(vertex) = segments.last.centerPos
      texCoords(vertex) = segments.last.centerTexturePos
      vertex += 1
    }


    val mesh = new Mesh()
    mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices))
    mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords))
    mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(indexes))
    mesh
  }
  


}