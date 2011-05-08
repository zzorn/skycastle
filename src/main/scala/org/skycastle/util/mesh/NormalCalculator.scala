package org.skycastle.util.mesh

import com.jme3.math.Vector3f
import java.util.HashMap

/**
 * Calculates normal vectors for a shape.
 */
object NormalCalculator {

  def calculateNormals(vertexes: Array[Vector3f], triangleIndexes: Array[Int] ): Array[Vector3f] = {
    val normals = new Array[Vector3f](vertexes.size)

    // Initialize normals
    var ni = 0
    while (ni < normals.size) {
      normals(ni) = new Vector3f()
      ni += 1
    }

    def setNormal(vertexIndex: Int, normal: Vector3f) {
      normals(vertexIndex).addLocal(normal)
    }

    // Calculate normal for each triangle
    var i = 0
    val sideAB = new Vector3f()
    val sideAC = new Vector3f()
    while (i < triangleIndexes.size) {
      val ai = triangleIndexes(i)
      val bi = triangleIndexes(i + 1)
      val ci = triangleIndexes(i + 2)
      val a = vertexes(ai)
      val b = vertexes(bi)
      val c = vertexes(ci)

      b.subtract(a, sideAB)
      c.subtract(a, sideAC)

      // Triangles with no surface area do not affect the normals of nearby sides
      val normal = sideAB.cross(sideAC)
      if (normal.lengthSquared > 0) {
        setNormal(ai, normal)
        setNormal(bi, normal)
        setNormal(ci, normal)
      }


      i += 3
    }

    // Normalize
    var normalIndex = 0
    while (i < normals.size) {
      val normal = normals(normalIndex)
      normal.normalizeLocal
      normalIndex += 1
    }

    normals
  }

}