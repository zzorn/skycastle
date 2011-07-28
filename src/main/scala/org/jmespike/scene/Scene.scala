package org.jmespike.scene

import com.jme3.scene.Spatial
import org.jmespike.conf.Conf
import com.jme3.asset.AssetManager
import org.jmespike.Context

/**
 * Creates a scene
 */
trait Scene extends Conf {

  def createScene(): Spatial =  createScene(Context.assetManager)

  def createScene(assetManager: AssetManager): Spatial

}