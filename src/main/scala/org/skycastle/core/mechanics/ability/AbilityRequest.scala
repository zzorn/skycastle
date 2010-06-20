package org.skycastle.core.mechanics.ability

import org.skycastle.util.{Above, ValueRange}

sealed trait AbilityResult
case class AbilitySuccess(workDone_J: Double, extraSuccess: Double) extends AbilityResult
abstract case class AbilityFailure() extends AbilityResult
case object WrongWork extends AbilityFailure
case class Fumble(fumbleAmount: Double) extends AbilityFailure

/**
 * 
 */
case class AbilityRequest(
        abilityType: AbilityType,
        energy_J: ValueRange, // Energy required from one ability use.
        precision_m: Double, // allowed tolerance (typically this is a Below(desired accuracy) range)
        effectSize_m: ValueRange, // Approximate size of toolhead / effect feature area side
        acceptableSuccess: Double = 0.5) // Indicates the success required.  0 -> will always work, 0.5 -> normal, 1 -> impossibly hard
{

  def matches(workDone: AbilityUsage): Boolean = {
    abilityType == workDone.abilityType &&
    energy_J.contains(workDone.energy_J) &&
    precision_m >= workDone.precision_m &&
    effectSize_m.contains(workDone.effectSize_m)
  }

  def result(workDone: AbilityUsage): AbilityResult = {
    if (matches(workDone)) {
      val success = workDone.success - acceptableSuccess
      if (success >= 0) AbilitySuccess(workDone.energy_J, success)
      else Fumble(-success)
    }
    else WrongWork
  }

  // TODO: Add method for checking failure amount etc.

}
