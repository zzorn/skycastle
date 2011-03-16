package org.skycastle.core.design

import org.skycastle.core.entity.Entity
import org.skycastle.core.space.{Item, AssemblySpace}

/**
 * An arrangement of entities grouped together.
 */
class AssemblyDesign extends Design {

  var parts: List[Design] = Nil

  def create() = {
    val assemblyEntity = new Entity()
    val assemblySpace: AssemblySpace = new AssemblySpace()
    assemblyEntity.addFacet(assemblySpace)
    val entities: List[Entity]  = parts map (_.create())
    entities.foreach {e: Entity => assemblySpace.add( e.getFacet[Item].get )}
  }

}
