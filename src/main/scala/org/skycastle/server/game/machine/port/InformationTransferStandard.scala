package org.skycastle.server.game.machine.port

/**
 * 
 */

abstract case class InformationTransferStandard

case class MechanicalPushSignal extends InformationTransferStandard
case class MechanicalRotarySignal extends InformationTransferStandard
case class ElectricalDigitalSignal extends InformationTransferStandard
case class ElectricalAnalogSignal extends InformationTransferStandard
case class DataPacketSignal extends InformationTransferStandard
case class DataPacketNetwork extends InformationTransferStandard

