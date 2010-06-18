package org.skycastle.core.mechanics.state

/**
 * An object that is in gaseous form.
 * E.g. some amount of helium in a balloon.
 * It will fill the container it is in, so has no volume specified, only the mass that it gets from being a physical object.
 */
case class Gaseous() extends State