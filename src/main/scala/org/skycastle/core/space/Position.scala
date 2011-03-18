package org.skycastle.core.space

import _root_.org.skycastle.core.entity.Facet
import com.jme3.math.Vector3f

/**
 * Something that is located in a Space.
 * Has a 3D location, and methods to get nearby items.
 * Physics or appearance is not applied to Items automatically (so they can be used for virtual objects like waypoints, etc)
 */
class Position(var position: Vector3f = Vector3f.ZERO, var space: Space = null) extends Facet {

 // override protected def onDeleted() = if (space != null) space.remove(this)

  def facetName = 'position

}
