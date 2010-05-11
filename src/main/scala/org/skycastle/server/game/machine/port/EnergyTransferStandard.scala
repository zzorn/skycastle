package org.skycastle.server.game.machine.port

import org.skycastle.shared.units.Units._

/**
 * A standard for transferring energy.
 */
case class EnergyTransferStandard(name: String, transferWay: EnergyTransferWay, period: Hz)

