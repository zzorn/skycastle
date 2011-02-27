package org.skycastle.core.entity.types

import org.skycastle.util.Logging
import org.skycastle.core.entity.Entity
import com.jme3.asset.AssetManager

/**
 * Keeps track of available entity types.
 */
object EntityTypeManager extends Logging {

  private var entityTypes: Map[Symbol, EntityType] = Map()

  def addEntityType(entityType: EntityType) {
    entityTypes += (entityType.name -> entityType)
  }

  def loadEntityTypes(assetManager: AssetManager) {
    // TODO: How to get all assets under a path ?


  }


  def createEntity(typeName: Symbol, instanceParameters: Map[Symbol, Any]): Entity = {
    val entityType = entityTypes.get(typeName).getOrElse(throw new IllegalArgumentException("EntityType named '"+typeName+"' not found"))
    val entity = entityType.createInstance(instanceParameters)
    logDebug("Created instance of entity "+typeName.name + " with parameters: "+instanceParameters.mkString(", "))
    entity
  }

}