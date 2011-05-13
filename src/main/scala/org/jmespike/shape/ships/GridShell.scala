package org.jmespike.shape.ships

import decorations.BoxDeco
import simplex3d.math.floatx.functions._
import org.jmespike.utils.XorShiftRandom

/**
 * A surface divided up in grids.
 */
// TODO: Support patterns?
// TODO: Pass in a random seed and some surface purpose / coordinate parameters to each ship component?  Or just a different / modified style?
class GridShell extends ShipComponent {

  val gridContent = p[ShipComponent]('gridContent, new AlternativePart)
  val marginSize = p('marginSize, 0.05f).editor(makeSlider(0f, 1f))
  val sizeU= p('sizeU, 8)
  val sizeV= p('sizeV, 8)


  def buildMesh(style: ShipShapeConf, base: ComponentBase, seed: Int) {
    val meshBuilder = base.meshBuilder

    val r = new XorShiftRandom(seed)
    // Create sharp edge
    val edge = base.extractBorder(0)

    // Create margin
    val gridBase = edge.extractBorder(marginSize())

    // Divide inner area in grids
    val grids = gridBase.extractGrid(sizeU(), sizeV())

    grids.foreach(g => gridContent().buildMesh(style, g, r.nextInt()))
  }
}