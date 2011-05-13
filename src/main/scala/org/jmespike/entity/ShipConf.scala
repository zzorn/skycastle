package org.jmespike.entity

import org.jmespike.appearance.AppearanceConf
import org.jmespike.controls.SteeringConf
import org.jmespike.shape.ships.ShipShapeConf

/**
 * Configuration for a ship.
 */
class ShipConf extends EntityConf {

  val appearance = control('appearance, new AppearanceConf())
  val steering   = control('steering, new SteeringConf())

  appearance.shape := new ShipShapeConf()

}