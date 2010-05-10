package org.skycastle.server.game.components

import _root_.org.skycastle.server.game.Facet
import _root_.org.skycastle.shared.math.Vector3

/**
 * Can move, has a velocity etc.
 */
trait Moving extends Facet {
  var pos: Vector3 = Vector3()
}

