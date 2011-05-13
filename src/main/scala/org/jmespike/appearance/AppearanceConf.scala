package org.jmespike.appearance

import org.jmespike.conf.Conf
import com.bulletphysics.collision.shapes.SphereShape
import org.jmespike.shape.{SphereConf, ShapeConf}
import com.jme3.scene.{Geometry, Spatial}
import java.util.Random
import org.jmespike.utils.XorShiftRandom
import org.jmespike.controls.ControlConf
import com.jme3.bounding.BoundingBox

/**
 * 
 */

class AppearanceConf extends ControlConf {

  val material = p('material, new MaterialConf)

  // TODO: Editor that allows selecting a base from a library / basic set of shapes.
  val shape = p[ShapeConf]('shape, new SphereConf())

  def createSpatial(seed: Int): Spatial = {
    val mesh = shape().createShape(seed + 1)
    val geom = new Geometry("geometry", mesh)
    geom.setMaterial(material().createMaterial(seed + 2))
    geom.setModelBound(new BoundingBox())
    geom
  }

  def createControl(seed: Int) = new AppearanceControl(this)

}