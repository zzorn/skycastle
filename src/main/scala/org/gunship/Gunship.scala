package org.gunship

import gun.Gun
import org.jmespike.conf.Conf
import org.jmespike.entity.EntityConf
import simplex3d.math.float.functions._
import simplex3d.math.float._

/**
 * The players ship.
 */
class Gunship extends EntityConf {

  val maxHealth = p('maxHealth, 100)

  val health = p('health, 100)

  var pos: Vec3 = Vec3(0,0,0)
  var heading: Quat4 = Quat4.Identity

  private var guns: List[Gun] = Nil

  def addGun(gun: Gun) {
    guns ::= gun
  }

}

