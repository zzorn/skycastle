package org.jmespike.shape.ships

import org.jmespike.conf.Conf
import org.jmespike.appearance.MaterialConf
import java.util.Random
import com.jme3.scene.{Geometry, Spatial}
import org.jmespike.utils.{XorShiftRandom, MeshBuilder}
import com.jme3.bounding.BoundingBox

/**
 * Generates ship shapes.
 */
class ShipConf extends Conf {

  private def makeDefaultCore: Core = {
    val core = new Core()
    core.front := new Chassis()
    core.right := new Chassis()
    core.left := new Chassis()
    core
  }

  val seed = p('seed, 42)
  val material = p('material, new MaterialConf)
  val core = p('core, makeDefaultCore)

  // TODO Graphical style etc?


  def createModel(): Spatial = {


    val rng =  new XorShiftRandom(seed())

    // Create the model, starting by the core and adding connected parts, passing in this ShipConf to provide style information to all parts.
    val meshBuilder = new MeshBuilder()
    core().buildMesh(this, meshBuilder)

    val mesh = meshBuilder.createMesh()

    val geom = new Geometry("geometry", mesh)
    geom.setMaterial(material().createMaterial(rng))

    // Setup bounding volume
    geom.setModelBound(new BoundingBox())

    geom

  }

}