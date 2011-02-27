package org.skycastle.core.entity

import scala.collection.JavaConversions._
import com.jme3.asset.{AssetManager, AssetKey}

/**
 * Creates an entity from the specified configuration file.
 */
@Deprecated
object EntityBuilder {

  def createEntity(path: String, assetManager: AssetManager): Entity = {

    // Get config for entity
    val entity = assetManager.loadAsset[Entity](new AssetKey[Entity](path))

    if (entity == null) throw new IllegalArgumentException("Path not found " + path)

    entity
  }

}



