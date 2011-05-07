package org.jmespike

import com.jme3.asset.AssetManager

/**
 * Singleton for accessing services.
 */
object Context {

  def assetManager: AssetManager = Main.getAssetManager

}