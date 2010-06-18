package org.skycastle.core.mechanics.state

/**
 * Some object that consists of a number of small particles.
 * E.g. a pile of berries, wheat, flour, pebbles, or sand.
 */
case class Grainy(grainDiameter_m: Double, variance_Percent: Double) extends State