package org.gunship.gun

import org.jmespike.conf.Conf

/**
 * Some kind of power up for a player / gun.
 */
class PowerUp extends Conf {

  val icon = p('icon, "")
  
  val shotVelocityAdjust = p('shotVelocityAdjust, 0)
  val shotDamageAdjust = p('shotDamageAdjust, 0)
  val shotDelayAdjust = p('shotDelayAdjust, 0)

  val duration = p('duration, 100)
  
}
