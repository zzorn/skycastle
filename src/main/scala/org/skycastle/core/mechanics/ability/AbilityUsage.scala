package org.skycastle.core.mechanics.ability

/**
 * Signifies the result of the usage of some ability.
 * Specifies request, time used and the success of the ability usage.
 */
case class AbilityUsage(request: AbilityRequest, time_s: Double, success_percent: Double)