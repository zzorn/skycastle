package org.skycastle.server.game.terrain

import _root_.org.skycastle.server.game.space.space3d.Space3D
import _root_.org.skycastle.shared.math.Vector3

/**
 * 
 */
class TerrainSpace extends Space3D {

  override type ItemType = TerrainItem

  def getHeight(pos: Vector3): Float = 0

  def getEntities(pos: Vector3, radius: Double): List[TerrainItem]

}

