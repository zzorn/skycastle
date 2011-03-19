package org.skycastle.core.space

import org.skycastle.core.entity.{Entity, Facet}
import com.jme3.math.Vector3f

/**
 * Something that contains items at locations.
 */
trait Space extends Facet {


  def facetCategory = 'space

  def entities: Iterable[Entity]

  def add(entity: Entity)
  def remove(entity: Entity)


}
