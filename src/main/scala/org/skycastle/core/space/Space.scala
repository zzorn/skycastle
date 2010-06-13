package org.skycastle.core.space

import _root_.org.skycastle.core.entity.Facet

/**
 * Something that contains items at locations.
 */
trait Space extends Facet {

  def add(item: Item)
  def remove(item: Item)

/*
  protected override type DerivedType = Space
  protected override def asDerivedType: DerivedType = this
*/

}
