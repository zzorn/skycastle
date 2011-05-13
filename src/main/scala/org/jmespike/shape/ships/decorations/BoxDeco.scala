package org.jmespike.shape.ships.decorations

import simplex3d.math.floatx.functions._
import org.jmespike.utils.XorShiftRandom
import org.jmespike.shape.ships.ShipComponent
import org.jmespike.shape.ships.Shell
import org.jmespike.shape.ships.ShipShapeConf
import org.jmespike.shape.ships.ComponentBase

/**
 * A simple box.
 */
class BoxDeco extends ShipComponent {

  // TODO: Make these random variables:
  val border = p('border, 0.1f).editor(makeSlider(0f, 1f))
  val borderVar = p('borderVar, 0.1f).editor(makeSlider(0f, 1f))
  val height = p('height, 0.1f).editor(makeSlider(-1f, 1f))
  val heightVar = p('heightVar, 0.2f).editor(makeSlider(-1f, 1f))
  val taper = p('taper, 0f).editor(makeSlider(-1f, 1f))
  val taperVar = p('taperVar, 0.1f).editor(makeSlider(-1f, 1f))


  def buildMesh(style: ShipShapeConf, base: ComponentBase, seed: Int) {
    val meshBuilder = base.meshBuilder

    val r = new XorShiftRandom(seed)

    def rnd(v: Float, vr: Float):Float = v + r.nextGaussian.toFloat * vr

    // Create sharp edge
    val edge = base.extractBorder(0)

    // Create magin
    //val boxBase = edge.extractBorder(max(0f, rnd(border())))
    val boxBase = edge.extractBorder(rnd(border(), borderVar()))

//    val h = boxBase.averageSize * rnd(1)
    val h = boxBase.averageSize * rnd(height(), heightVar)
    val t = rnd(taper(), taperVar())
    val cube = boxBase.extractCube(h, t, t)
    cube.makeSolid()
  }
}