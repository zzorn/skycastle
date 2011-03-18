package org.skycastle.core.space

import _root_.org.skycastle.core.entity.Facet
import com.jme3.math.Vector3f

/**
 * Describes a location in a Space.
 */
class Position(var position: Vector3f = Vector3f.ZERO, var space: Space = null) extends Facet {

  def facetName = 'position

  // TODO: Remove from space if the entity is deleted
 // override protected def onDeleted() = if (space != null) space.remove(this)


}
