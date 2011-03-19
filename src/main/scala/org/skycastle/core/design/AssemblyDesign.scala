package org.skycastle.core.design

import org.skycastle.core.space.{AssemblySpace}
import org.skycastle.util.parameters.Parameters
import org.skycastle.core.entity.{NoEntity, Entity}
import org.skycastle.client.appearance.AssemblyAppearance

/**
 * An arrangement of entities grouped together.
 */
class AssemblyDesign extends Design {

  var parts: List[Design] = Nil

  def build(parameters: Parameters) = {

    val assemblySpace: AssemblySpace = new AssemblySpace()
    val entities: List[Entity]  = parts map (_.create(parameters)) filterNot (_ == NoEntity)
    entities.foreach {e: Entity => assemblySpace.add( e )}

    val assemblyEntity = new Entity()
    assemblyEntity.entityName = "AssemblyEntity" + "-" + assemblyEntity.hashCode

    assemblyEntity.space = assemblySpace
    assemblyEntity.appearance = new AssemblyAppearance()
    assemblyEntity
  }

}
