package org.jmespike

import com.jme3.asset.AssetManager

/**
 * Singleton for accessing services.
 */
object Context {

  var game: BaseGame = null

  def assetManager: AssetManager = game.getAssetManager

}