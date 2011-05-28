package org.jmespike.controls

import com.jme3.renderer.{ViewPort, RenderManager}
import com.jme3.scene.control.{Control, AbstractControl}
import org.jmespike.entity.EntityConf
import org.jmespike.conf.Conf
import org.scalaprops.{Property, Bean, BeanListener}
import java.lang.reflect.Constructor
import org.jmespike.utils.ClassUtils
import com.jme3.scene.{Node, Spatial}

/**
 * Convenience base class for Controls that are used to control entity nodes.
 * If a conf is supplied, will listen to changes to it and call confUpdated during an update after it is changed.
 * NOTE: Should only be used on spatials of type Node.
 */
// TODO: Should this class handle input actions also?
abstract class EntityControl[T <: ControlConf](conf: T) extends AbstractControl {

  private var enabledChanged = true // Call onEnabled during first update.
  private var confChanged = false
  private var confListenerRegistered = false

  private val confListener = new BeanListener {
    def onPropertyChanged(bean: Bean, property: Property[_]) {confChanged = true}
    def onPropertyRemoved(bean: Bean, property: Property[_]) {confChanged = true}
    def onPropertyAdded(bean: Bean, property: Property[_])   {confChanged = true}
  }

  /**
   * Called when the config has been changed.
   */
  def onConfChanged(conf: T) {}

  /**
   * Called if the control is enabled, during the update pass.
   */
  def controlUpdate(tpf: Float) {}

  /**
   * Called if the control is enabled, during the render pass.
   */
  def controlRender(rm: RenderManager, vp: ViewPort) {}

  /**
   * Called during update, before controlUpdate, if the control was enabled since the last update.
   * Also called during the first update of the control, after it was created.
   */
  def onEnabled() {}

  /**
   * Called during update, if the control was disabled since the last update.
   */
  def onDisabled() {}

  /**
   * A convenience method that returns the random seed for the spatial.
   */
  def randomSeed: Int = SeedCalculator.randomSeedOf(spatial)

  /**
   * Returns the node that this control is controlling.
   */
  def entity: Node = spatial.asInstanceOf[Node]

  /**
   * Returns the first entity control of the specified type for the entity that the current control is attached to,
   * if it was available, otherwise None.
   */
  def entityControl[E <: EntityControl[_]](implicit m: Manifest[E]): Option[E] = {
    val control = spatial.getControl(m.erasure.asInstanceOf[Class[E]])

    if (control == null) None
    else Some(control)
  }

  /**
   * Applies the specified code on the first entity control of the specified type, if found in this entity.
   */
  def withEntityControl[T <: EntityControl[_]](block: T => Unit)(implicit m: Manifest[T]) {
    entityControl[T](m) foreach {ec => block(ec)}
  }

  /**
   * Returns the EntityConf that is used to configure the entity that this control belongs to,
   * or null if this control is not controlling an entity.
   */
  def entityConf: EntityConf = spatial.getUserData("EntityConf").asInstanceOf[EntityConf]

  /**
   * Should create a copy of the control.
   * By default creates a new instance of the descendant by invoking a constructor with one conf parameter if found,
   * otherwise using a no-parameter constructor.
   */
  def createCopy: EntityControl[T] = {
    ClassUtils.newInstance[ControlConf](getClass,
                                        conf,
                                        getClass.newInstance.asInstanceOf[AnyRef],
                                        classOf[ControlConf]).asInstanceOf[EntityControl[T]]
  }

  final def cloneForSpatial(spatial: Spatial): Control = {
    val copy = createCopy
    spatial.addControl(copy)
    return copy
  }

  final override def setEnabled(enabled: Boolean) {
    if (isEnabled != enabled) {
      super.setEnabled(enabled)
      enabledChanged = true
    }
  }

  final override def update(tpf: Float) {
    // Handle change of enabled state
    if (enabledChanged) {
      enabledChanged = false

      // Register or unregister listener to the conf when the control is enabled or disabled
      if (conf != null) {
        if (isEnabled && !confListenerRegistered) {
          conf.addDeepListener(confListener)
          confListenerRegistered = true
        }
        else if (!isEnabled && confListenerRegistered) {
          conf.removeDeepListener(confListener)
          confListenerRegistered = false
        }
      }

      // Notify descendant about enable state change
      if (isEnabled) onEnabled()
      else onDisabled()
    }

    // Notify configuration changes
    if (isEnabled && conf != null && confChanged) {
      confChanged = false
      onConfChanged(conf)
    }

    // Invoke controlUpdate if this control is enabled
    super.update(tpf)
  }


}