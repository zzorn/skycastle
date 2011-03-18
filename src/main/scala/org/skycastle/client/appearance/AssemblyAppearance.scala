package org.skycastle.client.appearance

import com.jme3.asset.AssetManager
import com.jme3.scene.Node
import org.skycastle.core.entity.Entity

/**
 * 
 */

class AssemblyAppearance extends Appearance {


  def createSpatial(assetManager: AssetManager) = {
    val assemblyNode = new Node()

    entity.space.entities foreach { entity: Entity =>
      assemblyNode.attachChild(entity.appearance.createSpatial(assetManager))
    }

    assemblyNode
  }
}