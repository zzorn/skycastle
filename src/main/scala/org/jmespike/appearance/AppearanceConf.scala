package org.jmespike.appearance

import org.jmespike.conf.Conf
import com.bulletphysics.collision.shapes.SphereShape
import org.jmespike.shape.{SphereConf, ShapeConf}
import com.jme3.scene.{Geometry, Spatial}
import java.util.Random

/**
 * 
 */

class AppearanceConf extends Conf {

  val material = p('material, new MaterialConf)

  // TODO: Editor that allows selecting a base from a library / basic set of shapes.
  val shape = p[ShapeConf]('shape, new SphereConf())

  def createSpatial(rng: Random): Spatial = {
    val mesh = shape().createShape(rng)
    val geom = new Geometry("geometry", mesh)
    geom.setMaterial(material().createMaterial(rng))
    geom
  }

}