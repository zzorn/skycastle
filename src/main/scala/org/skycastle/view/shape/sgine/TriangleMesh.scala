package org.skycastle.view.shape.sgine

import org.sgine.property.AdvancedProperty
import org.sgine.render.Image
import org.sgine.render.primitive.{Mesh, Primitive}

/**
 * 
 */
class TriangleMesh extends PrimitiveComponent {

  var indexes: Seq[Int] = Nil
  var vertices: Seq[Double] = Nil

  val texture = new AdvancedProperty[Image](null, this)

  texture.listeners += invalidationHandler
  color.listeners += invalidationHandler

  protected def createPrimitive(): Primitive = {
    val mesh: Mesh = new Mesh(color())
    mesh.indexes = indexes
    mesh.vertices = vertices
    mesh
  }
}
