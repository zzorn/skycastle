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
// TODO: Specify scale and recursion level at which to just terminate and replace with solid surface.
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


  def createModel(s: Int): Spatial = {


    val rng =  new XorShiftRandom(seed() + s)

    // Create the model, starting by the core and adding connected parts, passing in this ShipConf to provide style information to all parts.
    val meshBuilder = new MeshBuilder()
    core().buildMesh(this, meshBuilder, rng.nextInt())

    val mesh = meshBuilder.createMesh()

    val geom = new Geometry("geometry", mesh)
    geom.setMaterial(material().createMaterial(rng))

    // Setup bounding volume
    geom.setModelBound(new BoundingBox())

    geom

  }

}