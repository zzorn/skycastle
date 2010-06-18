package org.skycastle.core.space

import _root_.org.skycastle.core.entity.Facet
import org.sgine.math.Vector3

/**
 * Something that is located in a Space.
 * Has a 3D location, and methods to get nearby items.
 */
class Item(var position: Vector3 = Vector3.Origo, var space: Space = null) extends Facet {

  override protected def onDeleted() = if (space != null) space.remove(this)

}
