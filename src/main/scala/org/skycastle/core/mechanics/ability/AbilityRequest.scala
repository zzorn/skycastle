package org.skycastle.core.mechanics.ability

import org.skycastle.util.ValueRange

/**
 * 
 */
case class AbilityRequest(
        abilityType: AbilityType,
        energy_J: Double, // Energy required from one ability use.
        precision_m: Double, // max allowed tolerance difference
        effectSize_m: ValueRange) // Approximate size of toolhead / effect feature area side
