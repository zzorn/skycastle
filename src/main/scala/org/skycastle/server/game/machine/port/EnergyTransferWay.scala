package org.skycastle.server.game.machine.port

import org.skycastle.shared.units.Units._

/**
 * 
 */
abstract case class EnergyTransferWay(energyType: EnergyType)

case class RotatingAxle(speed: MeterPerSecond, force: Newton, period: Hz) extends EnergyTransferWay(KineticEnergy)
case class OscillatingPiston(speed: MeterPerSecond, force: Newton, period: Hz) extends EnergyTransferWay(KineticEnergy)
case class MovingBelt(speed: MeterPerSecond, force: Newton) extends EnergyTransferWay(KineticEnergy)

case class DirectCurrent(volt: Volt, maxCurrent: Ampere) extends EnergyTransferWay(ElectricalEnergy)
case class AlternatingCurrent(volt: Volt, maxCurrent: Ampere, period: Hz) extends EnergyTransferWay(ElectricalEnergy)


