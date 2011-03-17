package org.skycastle.core.design

import org.skycastle.core.entity.Entity
import org.skycastle.core.space.{Item, AssemblySpace}
import org.skycastle.util.parameters.Parameters

/**
 * An arrangement of entities grouped together.
 */
class AssemblyDesign extends Design {

  var parts: List[Design] = Nil

  def create(context: Parameters) = {
    null
    /* TODO: Fix
    val assemblyEntity = new Entity()
    val assemblySpace: AssemblySpace = new AssemblySpace()
    assemblyEntity.addFacet(assemblySpace)
    val entities: List[Entity]  = parts map (_.create(context))
    entities.foreach {e: Entity => assemblySpace.add( e.getFacet[Item].get )}
    */
  }

}
