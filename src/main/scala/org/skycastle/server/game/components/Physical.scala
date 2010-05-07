package org.skycastle.server.game.components

import _root_.org.skycastle.server.game.Facet

/**
 * Consists of matter of some sort and has a shape.
 * May also have coatings (e.g. paintjob, clothes, etc).
 */
trait Physical extends Facet {

  var shape: Shape
  var material: Material

}

