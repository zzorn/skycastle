package org.skycastle.server.game.components

import _root_.org.skycastle.server.game.Facet
import _root_.org.skycastle.shared.math.Vector3

/**
 * An object that is located in a 3D world with a terrain.
 *
 * This aspect is assinged by the terrain space to entities that are added to it.
 *
 * Also handles movement.
 */
trait TerrainLocated extends Facet {

  Vector3 pos;

  Vector3 velocity;

  def update() { // Todo: time class to pass to update methods

  }

}

