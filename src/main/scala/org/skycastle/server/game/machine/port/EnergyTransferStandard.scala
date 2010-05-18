package org.skycastle.server.game.machine.port

import org.skycastle.shared.units.Units._

/**
 * 
 */
// TODO: Some kind of port standard?  Then just specify which one a given connector component implements?
abstract case class EnergyTransferStandard(energyType: EnergyType)

case class RotatingAxle(speed: MeterPerSecond, maxForce: Newton, period: Hz) extends EnergyTransferStandard(KineticEnergy)
case class OscillatingPiston(speed: MeterPerSecond, maxForce: Newton, period: Hz) extends EnergyTransferStandard(KineticEnergy)
case class MovingBelt(speed: MeterPerSecond, maxForce: Newton) extends EnergyTransferStandard(KineticEnergy)

case class DirectCurrent(volt: Volt, maxCurrent: Ampere) extends EnergyTransferStandard(ElectricalEnergy)
case class AlternatingCurrent(volt: Volt, maxCurrent: Ampere, period: Hz) extends EnergyTransferStandard(ElectricalEnergy)


