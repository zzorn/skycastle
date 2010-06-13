package org.skycastle.core.entity

import _root_.org.skycastle.core.data.{Value, Data}
import _root_.org.skycastle.core.platform.persistence.{Ref, Persistent}
import _root_.org.skycastle.core.platform.SkycastleContext
import org.skycastle.util.ListenableList

object Entity {

  def create(entityDefinition: Data): Entity = {
    val entity: Entity = new Entity()

    val evaluatedDefinition = entityDefinition.evaluate()

    // Create facets
    evaluatedDefinition.asInstanceOf[Data].values foreach { entry: (Symbol, Value) =>
      if (entry._2.isInstanceOf[Data]) {
        SkycastleContext.facetService.createFacet(entry._1, entry._2.asInstanceOf[Data]) match {
          case Some(facet) => entity.addFacet(facet)
          case None => println("Warning: Couldn't create facet " + entry._1.name + " for an entity.  Ignoring that facet.") // TODO: Use logging service
        }
      }
    }

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

  /**
   * Removes the entity and its facets from persistent storage.
   */
  override def delete() {
    // Delete facets
    _facets.list.foreach {f: Ref[Facet] => f().delete() }

    // Delete self
    super.delete()
  }


  override def toString(): String = {
    "Entity " + hashCode + "¸ facets: " + _facets.list.map(_.get()).mkString("[", ", ", "]")
  }

}

