package org.skycastle.core.space.simple

import _root_.org.skycastle.core.space.{Position, Space}
import org.skycastle.core.entity.Entity

/**
 * A simple space that just contains the items in a list.
 * Only use it for small areas or areas with few items.
 */
class SimpleSpace extends Space {

  private var _entities: List[Entity] = Nil

  def entities = _entities

  def remove(entity: Entity) = _entities = _entities.filterNot(_ == entity)
  def add(entity: Entity) = _entities = entity :: _entities

}

