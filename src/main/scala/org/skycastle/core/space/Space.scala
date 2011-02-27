package org.skycastle.core.space

import org.skycastle.core.entity.{Entity, Facet}
import com.jme3.math.Vector3f

/**
 * Something that contains items at locations.
 */
trait Space extends Facet {

  def add(item: Item)

  def add(entity: Entity, position: Vector3f) {
    add( entity.facet[Item] match {
      case Some(item) =>
        item.position = position
        item.space = this
        item
      case None =>
        val item = new Item(position, this)
        entity.addFacet(item)
        item
    } )
  }

  def remove(item: Item)

/*
  protected override type DerivedType = Space
  protected override def asDerivedType: DerivedType = this
*/

}
