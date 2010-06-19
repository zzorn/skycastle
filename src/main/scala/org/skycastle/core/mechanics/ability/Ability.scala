package org.skycastle.core.mechanics.ability

import org.skycastle.util.ValueRange

/**
 * An instance of an ability, with specified ranges of usage.
 */
case class Ability(
        abilityType: AbilityType,
        force_N: ValueRange,
        precision_m: ValueRange,
        effectSize_m: ValueRange,
        range_m: ValueRange,
        hardness: Double
        ) {


}