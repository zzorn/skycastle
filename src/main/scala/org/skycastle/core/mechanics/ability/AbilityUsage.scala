package org.skycastle.core.mechanics.ability

/**
 * Signifies the result of the usage of some ability.
 * Specifies the success of the usage - 0 is barely success, 1 is perfect success, -1 is total failure. 
 */
case class AbilityUsage(abilityType: AbilityType, 
                        energy_J: Double,
                        precision_m: Double,
                        effectSize_m: Double,
                        success: Double = 0.5)