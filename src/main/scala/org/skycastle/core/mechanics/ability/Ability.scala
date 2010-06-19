package org.skycastle.core.mechanics.ability

import org.skycastle.util.ValueRange

/**
 * An instance of an ability, with specified ranges of usage.
 */
case class Ability(
        abilityType: AbilityType,
        energy_J: ValueRange,
        precision_m: ValueRange,
        effectSize_m: Double) {


}