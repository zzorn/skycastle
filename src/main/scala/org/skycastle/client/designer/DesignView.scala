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

  var view: Node = new Node()

  def generateEntity() {
    completedObject = design.create(Parameters())
  }

  def generateView(assetManager: AssetManager) {
    generateEntity()

    val dg = new DesignGrid()

    view = new Node()
    view.attachChild(completedObject.appearance.createSpatial(assetManager))
    view.attachChild(dg.createSpatial(assetManager))
    view.move(0, -2, 5)
  }



}