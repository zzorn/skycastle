package org.skycastle.core.entity

import _root_.org.skycastle.core.data.{Value, Data}
import _root_.org.skycastle.core.platform.persistence.{Ref, Persistent}
import _root_.org.skycastle.core.platform.SkycastleContext
import org.skycastle.util.ListenableList
import org.skycastle.core.space.Item

object Entity {

  def create(entityDefinition: Data): Entity = {
    val entity: Entity = new Entity()

    // Create facets
    def createFacet(entry: (Symbol, Value)): Option[Facet] = SkycastleContext.facetService.createFacet(entry._1, entry._2)
    Entity((entityDefinition.evaluate().asInstanceOf[Data].values map createFacet).flatten)
  }

  def apply(facets: Facet*): Entity = Entity(facets.toList)

  def apply(facets: Iterable[Facet]): Entity = {
    val entity: Entity = new Entity()

    // Set facets
    facets foreach {(f:Facet) => entity.addFacet(f)}

    // Store entity
    SkycastleContext.platformServices.store(entity)

    entity
  }

}


/**
 *  A persistent object consisting of different parts (facets).
 */
class Entity extends Persistent {

  private var _facets = new ListenableList[Ref[Facet]](true, true)

  def addFacet(facet: Facet) {
    _facets.add(facet.ref)
    facet.entity = this.ref
  }

  def removeFacet(facet: Facet) {
    _facets.remove(facet.ref)
    facet.entity = null
  }

  def facets: List[Ref[Facet]] = _facets()

  def getFacet[T <: Facet](): Option[T] = facets.map(_.get).find(f => f.isInstanceOf[T]).asInstanceOf[Option[T]]

  /**
   * Removes the entity and its facets from persistent storage.
   */
  override final def delete() {
    // Delete facets
    _facets.list.foreach {f: Ref[Facet] => f().delete() }

    // Delete self
    super.delete()
  }

  override def toString(): String = {
    "Entity " + hashCode + "¸ facets: " + _facets.list.map(_.get()).mkString("[", ", ", "]")
  }

  /**
   * Replaces this entity with the specified new entities, at the same location.
   */
  def replaceWith(entities: Entity *) {
    getFacet[Item]() match {
      case Some(item) => {
        // Spawn replacements:
        entities foreach {item.space.add(_, item.position)}
      }
      case None => // This entity isn't in a space, so dont put the replacement in a space
    }

    // Remove self
    delete()
  }

}

