package org.skycastle.core.entity.types

import org.skycastle.core.entity.Entity
import com.jme3.asset.AssetManager
import tools.nsc.io.{Directory, File}
import org.skycastle.util.Logging
import org.skycastle.util.parameters.Parameters

/**
 * Keeps track of available entity types.
 */
object ArchetypeManager extends Logging {

  private var archetypes: Map[Symbol, Archetype] = Map()

  def addEntityType(entityType: Archetype) {
    archetypes += (entityType.name -> entityType)
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

  def createEntity(archetypeName: Symbol): Entity = createEntity(archetypeName, null)

  def createEntity(archetypeName: Symbol, entityParameters: EntityParameters): Entity = {
    val archetype = archetypes.get(archetypeName).getOrElse(throw new IllegalArgumentException("Archetype named '"+archetypeName.name+"' not found"))
    val entity = archetype.createEntity(entityParameters)
    entity
  }

}