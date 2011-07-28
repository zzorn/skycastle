package org.jmespike.appearance

import com.jme3.scene.control.AbstractControl
import com.jme3.renderer.{ViewPort, RenderManager}
import org.jmespike.controls.EntityControl
import org.scalaprops.{Property, Bean, BeanListener}
import com.jme3.scene.{Node, Spatial}
import org.jmespike.Context

/**
 * Attaches the specified appearance to a Node, and re-creates it if the appearance conf changes.
 * Does nothing if the spatial the control is attached to is not a Node.
 */
class AppearanceControl(appearance: AppearanceConf) extends EntityControl(appearance) {

  private var appearanceSpatial: Spatial = null

  private def reloadAppearance() {
    if (appearanceSpatial != null) entity.detachChild(appearanceSpatial)
    appearanceSpatial = appearance.createSpatial(randomSeed, Context.assetManager)
    if (appearanceSpatial != null) entity.attachChild(appearanceSpatial)
  }

  override def onConfChanged(conf: AppearanceConf) {
    reloadAppearance()
  }

  override def onEnabled() {
    reloadAppearance()
  }

  override def onDisabled() {
    if (appearanceSpatial != null) entity.detachChild(appearanceSpatial)
    appearanceSpatial = null
  }


}