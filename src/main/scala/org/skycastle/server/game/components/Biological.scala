package org.skycastle.server.game.components

import _root_.org.skycastle.server.game.Facet

/**
 * Used for entities that have some kind of life and attributes.
 * Keeps track of wounds, sleep, hunger, and other biological qualities and updates attributes and alife status based on that.
 * Provides metabolism functions, like eating.
 * Nutritions in the food affect the attributes.
 */
// TODO: Maybe extract a common aspect for things that have attributes (actors), and different aspects that can update them (biological, zombie, robot, etc)
trait Biological extends Facet {
  
}

