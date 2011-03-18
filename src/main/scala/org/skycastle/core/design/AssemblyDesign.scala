package org.skycastle.core.design

import org.skycastle.core.space.{Position, AssemblySpace}
import org.skycastle.util.parameters.Parameters
import com.jme3.math.Vector3f
import org.skycastle.core.entity.{NoEntity, Entity}

/**
 * An arrangement of entities grouped together.
 */
class AssemblyDesign extends Design {

  var parts: List[Design] = Nil

  def build(parameters: Parameters) = {

    val assemblySpace: AssemblySpace = new AssemblySpace()
    val entities: List[Entity]  = parts map (_.create(parameters)) filterNot (_ == NoEntity)
    entities.foreach {e: Entity => assemblySpace.add( e.position )}

    val assemblyEntity = new Entity()
    assemblyEntity.space = assemblySpace
    assemblyEntity
  }

}
