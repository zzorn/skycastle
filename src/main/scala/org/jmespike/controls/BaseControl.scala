package org.jmespike.controls

import com.jme3.renderer.{ViewPort, RenderManager}
import com.jme3.scene.Spatial
import com.jme3.scene.control.{Control, AbstractControl}

/**
 * Convenience base class for Controls.
 */
abstract class BaseControl extends AbstractControl {

  def createCopy: BaseControl

  final def cloneForSpatial(spatial: Spatial): Control = {
    val copy = createCopy
    spatial.addControl(copy)
    return copy
  }

  def controlRender(rm: RenderManager, vp: ViewPort) {}

  def controlUpdate(tpf: Float) {}
}