package org.gunship.gun

import java.util.Random

/** The order in which barrels are fired if there are more than one. */
trait BarrelSequence {
  def shootingBarrel(shotNumber: Int, barrelCount: Int): Set[Int]
}

object BarrelSequence {
  val values = List[BarrelSequence](
    AlternatingBarrelSequence,
    SimultaneousBarrelSequence,
    RandomBarrelSequence)
}

case object AlternatingBarrelSequence extends BarrelSequence {
  def shootingBarrel(shotNumber: Int, barrelCount: Int) = Set(shotNumber % barrelCount)
}

case object SimultaneousBarrelSequence extends BarrelSequence {
  def shootingBarrel(shotNumber: Int, barrelCount: Int) = {
    var allBarrels = Set[Int]()
    0 to barrelCount foreach {b => allBarrels += b}
    allBarrels
  }
}

case object RandomBarrelSequence extends BarrelSequence {
  private val r = new Random()

  def shootingBarrel(shotNumber: Int, barrelCount: Int) = Set(r.nextInt(barrelCount))
}


