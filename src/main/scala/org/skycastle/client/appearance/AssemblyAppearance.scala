package org.skycastle.client.appearance

import com.jme3.asset.AssetManager
import com.jme3.scene.{Node, Spatial}
import org.skycastle.core.entity.Entity
import com.jme3.bounding.BoundingBox

/**
 * An appearance for an assembly of entities.
 * Just renders the parts as a node.
 */
class AssemblyAppearance extends BasicAppearance {

  def doCreateSpatial(assetManager: AssetManager): Spatial =  {
    val assemblyNode = new Node()

    // Add the appearances for the contained entities
    entity.space.entities foreach { entity: Entity =>
      if (entity.hasFacet('appearance))
        assemblyNode.attachChild(entity.appearance.createSpatial(assetManager))
    }

    // Add some bounds
    assemblyNode.setModelBound(new BoundingBox)
    
    assemblyNode
  }

}