package org.jmespike.entity

import org.jmespike.appearance.AppearanceConf
import org.jmespike.controls.SteeringConf

/**
 * Conf for entities in space, mainly artifacts thou?
 */
class SpaceEntity extends EntityConf {

  val appearance = control('appearance, new AppearanceConf())
  val steering   = control('steering, new SteeringConf())

}