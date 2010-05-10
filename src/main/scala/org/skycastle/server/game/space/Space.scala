package org.skycastle.server.game.space

import _root_.org.skycastle.server.game.Facet
import _root_.org.skycastle.shared.math.Vector3

/**
 * 
 */
trait Space[T <: SpaceItem] extends Facet {


  def getEntities(pos: Vector3, radius: Double): List[T]

}

