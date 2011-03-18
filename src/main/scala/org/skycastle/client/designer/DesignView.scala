package org.skycastle.client.designer

import org.skycastle.core.entity.{NoEntity, Entity}
import org.skycastle.core.design.{EmptyDesign, Design}
import org.skycastle.util.parameters.Parameters
import com.jme3.scene.{Node, Spatial}
import org.skycastle.client.appearance.Appearance
import com.jme3.asset.AssetManager

/**
 * A view that can be used to add components to a design.
 */
class DesignView {

  var completedObject: Entity = null

  var design: Design = null

  var view: Spatial = new Node()

  def generateEntity() {
    completedObject = design.create(Parameters())
  }

  def generateView(assetManager: AssetManager) {
    generateEntity()
    view = completedObject.facet[Appearance].createSpatial(assetManager)
  }



}