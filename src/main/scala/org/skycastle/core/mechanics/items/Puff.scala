package org.skycastle.core.mechanics.items

import org.skycastle.core.entity.Facet

/**
 * An object that is gaseous in nature and has an approximate form of a sphere.
 * e.g. a puff of smoke or gas in a surrounding atmosphere.
 * (trails of smoke could be made up of puffs of smoke - easy to render too).
 */
case class Puff(diameter_m: Double) extends Facet