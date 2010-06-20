package org.skycastle.core.mechanics.project

import scala.math._
import org.skycastle.core.mechanics.ability.{AbilitySuccess, AbilityUsage, AbilityRequest, AbilityFailure}

/**
 * A project that just requires some amount of one type of work.
 */
class SimpleProject(typeOfWork: AbilityRequest,
                    totalEnergyNeeded_J: Double,
                    lostEnergyPerAbilityUsage_J: Double = 0,
                    onCompleted: => Unit = {},
                    onAborted: => Unit = {} ) /*extends Project*/ {

  if (totalEnergyNeeded_J <= 0) throw new IllegalArgumentException("totalEnergyNeeded_J should be larger than 0, but was " + totalEnergyNeeded_J)
  if (lostEnergyPerAbilityUsage_J < 0) throw new IllegalArgumentException("lostEnergyPerAbilityUsage_J should be larger or equal to 0, but was " + lostEnergyPerAbilityUsage_J)

  private var remainingEnergyNeeded_J = totalEnergyNeeded_J
  private var _completed: Boolean = false
  private var _aborted: Boolean = false

  def workNeeded: Iterable[AbilityRequest] = if (active) List(typeOfWork) else Nil

  def addWork(workDone: AbilityUsage) {
    if (active) {
      typeOfWork.result(workDone) match {
        case AbilitySuccess(energy, success) =>
          val providedEnergy = max(0, energy - lostEnergyPerAbilityUsage_J)
          remainingEnergyNeeded_J = max(0, remainingEnergyNeeded_J - providedEnergy)
          if (remainingEnergyNeeded_J <= 0) complete()
        case AbilityFailure() => // Nothing happens
      }
    }
  }

  def progress: Double = 1.0 - remainingEnergyNeeded_J / totalEnergyNeeded_J

  def completed: Boolean = _completed
  def aborted: Boolean = _aborted
  def active: Boolean = !_completed && !_aborted

  def abort() {
    if (active) {
      _aborted = true
      onAborted
    }
  }

  private def complete() {
    _completed = true
    onCompleted
  }

}