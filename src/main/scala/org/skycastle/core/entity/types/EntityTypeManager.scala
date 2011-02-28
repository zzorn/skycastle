package org.skycastle.core.entity.types

import org.skycastle.util.Logging
import org.skycastle.core.entity.Entity
import com.jme3.asset.AssetManager
import tools.nsc.io.{Directory, File}

/**
 * Keeps track of available entity types.
 */
object EntityTypeManager extends Logging {

  private var entityTypes: Map[Symbol, EntityType] = Map()

  def addEntityType(entityType: EntityType) {
    entityTypes += (entityType.name -> entityType)
  }

  /**
   * Load and register entity types in a directory and its subdirectories.
   */
  def loadDirectory(directory: Directory) {
    logInfo("Loading Entity Types from directory " + directory + " and subdirectories")
    directory.deepFiles foreach (f => loadFile(f, directory.path))
    logInfo("Entity Types loaded")
  }

  /**
   * Load and register entity type in a file.
   */
  def loadFile(file: File, pathPrefixToRemove: String) {
    val entityType = EntityTypeLoader.load(file.inputStream, file.path.stripPrefix(pathPrefixToRemove))
    addEntityType(entityType)
  }

  def createEntity(typeName: Symbol, instanceParameters: Map[Symbol, Any]): Entity = {
    val entityType = entityTypes.get(typeName).getOrElse(throw new IllegalArgumentException("EntityType named '"+typeName+"' not found"))
    val entity = entityType.createInstance(instanceParameters)
    logDebug("Created instance of entity "+typeName.name + " with parameters: "+instanceParameters.mkString(", "))
    entity
  }

}