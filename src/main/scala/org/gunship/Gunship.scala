package org.gunship

import org.jmespike.conf.Conf
import org.jmespike.entity.EntityConf

/**
 * The players ship.
 */
class Gunship extends EntityConf {

  val maxHealth = p('maxHealth, 100)

  val health = p('health, 100)

}

