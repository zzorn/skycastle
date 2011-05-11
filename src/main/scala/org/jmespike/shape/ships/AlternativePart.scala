package org.jmespike.shape.ships

import decorations.BoxDeco
import org.jmespike.utils.XorShiftRandom._
import org.jmespike.utils.XorShiftRandom

/**
 * Selects from one of several alternative parts, based on seed and probabilities.
 */
class AlternativePart extends ShipComponent {

  val a = p[ShipComponent]('a, new Shell)
  val b = p[ShipComponent]('b, new BoxDeco)
  val c = p[ShipComponent]('c, new BoxDeco)
  val d = p[ShipComponent]('d, new Shell)
  val e = p[ShipComponent]('e, new Shell)
  val aProb = p('aProb, 80f).editor(makeSlider(0, 200))
  val bProb = p('bProb, 30f).editor(makeSlider(0, 200))
  val cProb = p('cProb, 10f).editor(makeSlider(0, 200))
  val dProb = p('dProb, 10f).editor(makeSlider(0, 200))
  val eProb = p('eProb, 5f).editor(makeSlider(0, 200))


  private def makeRecursiveComponent: ShipComponent = {
    val q = new AlternativePart
    q.d := new Shell
    q.e := new Shell
    val p = new GridShell
    p.sizeU := 2
    p.sizeV := 1
    p.gridContent := q
    p
  }


  override def buildMesh(style: ShipConf, base: ComponentBase, seed: Int) {

    // TODO: Add better editing tools so ship components can be selected.  For now, working around recursive constructor call:
//    if (d() == null) d:= makeRecursiveComponent
//    if (e() == null) e:= makeRecursiveComponent

    val r = new XorShiftRandom(seed)

    val totalProb = aProb() + bProb() + cProb() + dProb() + eProb()

    if (totalProb > 0) {
      val dice = r.nextFloat() * totalProb
      val seed = r.nextInt()
      var p = 0f

      def checkMore(prob: Float, part: ShipComponent): Boolean = {
        p += math.max(0f, prob)
        if (dice < p) {
          part.buildMesh(style, base, seed)
          false
        }
        else true
      }

      if (checkMore(aProb(), a()))
        if (checkMore(bProb(), b()))
          if (checkMore(cProb(), c()))
            if (checkMore(dProb(), d()))
              if (checkMore(eProb(), e()))
                throw new IllegalStateException("Too high dice throw, probability was "+p+ ", and upper limit "+ totalProb)
    }
    else base.makeSolid()
  }

}