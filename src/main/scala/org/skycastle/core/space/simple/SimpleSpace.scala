package org.skycastle.core.space.simple

import _root_.org.skycastle.core.space.{Position, Space}

/**
 * A simple space that just contains the items in a list.
 * Only use it for small areas or areas with few items.
 */
class SimpleSpace extends Space {

  private var items: List[Position] = Nil

  def remove(item: Position) = items = items.filterNot(_ == item)
  def add(item: Position) = items = item :: items

}

