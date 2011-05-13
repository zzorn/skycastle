package org.jmespike.shape.ships

import org.jmespike.conf.Conf
import org.jmespike.appearance.MaterialConf
import java.util.Random
import com.jme3.scene.{Geometry, Spatial}
import org.jmespike.utils.{XorShiftRandom, MeshBuilder}
import com.jme3.bounding.BoundingBox
import org.jmespike.shape.ShapeConf

/**
 * Generates ship shapes.
 */
// TODO: Specify scale and recursion level at which to just terminate and replace with solid surface.
class ShipShapeConf extends ShapeConf {

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


  def createShape(seed: Int) = {
    val meshBuilder = new MeshBuilder()

    core().buildMesh(this, meshBuilder, seed)

    meshBuilder.createMesh()
  }

}