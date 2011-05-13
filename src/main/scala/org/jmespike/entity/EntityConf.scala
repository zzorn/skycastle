package org.jmespike.entity

import org.jmespike.conf.Conf
import org.jmespike.appearance.{AppearanceControl, AppearanceConf}
import com.jme3.scene.{Spatial, Node}
import org.jmespike.controls.{SeedCalculator, ControlConf,  SteeringConf}
import com.jme3.scene.control.AbstractControl._
import org.scalaprops.Property

/**
 * Configures an entity.
 * Can be subclassed, any property of type ControlConf is used to create and add a Control to the generated entity.
 */
class EntityConf extends Conf {

  /**
   * Alias for defining controls for the entity.
   */
  def control[T <: ControlConf](name: Symbol, value: T)(implicit m: Manifest[T]): Property[T] = p(name, value)(m)

  def createEntity(): Spatial = {

    val entity = new Node()

    // Store a reference to the entity configuration
    // TODO: This only accepts jme serializable content... if needed, use own system
//   entity.setUserData("EntityConf", this)

    // Create and add a control for each control configuration in this entity conf
    val seed = SeedCalculator.randomSeedOf(entity)
    controlConfigurations foreach {controlConf =>
      entity.addControl(controlConf.createControl(seed))
    }

    entity
  }

  /**
   * Get the values of the properties that are of type ControlConf and have non-null values.
   */
  private def controlConfigurations: Iterable[ControlConf] = {
    properties.values.
            filter(p => p.value != null &&
                        classOf[ControlConf].isAssignableFrom(p.kind.erasure)).
            map(p => p.value.asInstanceOf[ControlConf])
  }

}
