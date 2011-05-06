package org.jmespike.shape

import com.jme3.scene.shape.Sphere
import com.jme3.scene.Mesh
import java.util.Random
import math._

/**
 * 
 */
class SphereConf extends ShapeConf {

  val radius        = p('radius, 1f).editor(makeSlider(0, 100))
  val radialSamples = p('radialSamples, 16)
  val zSamples      = p('zSamples, 16)
  val useEvenSlices = p('useEvenSlices, false)
  val interior      = p('interior, false)

  def createShape(rng: Random): Mesh = {
    new Sphere(max(zSamples, 1), max(radialSamples, 1), radius, useEvenSlices, interior)
  }
}
