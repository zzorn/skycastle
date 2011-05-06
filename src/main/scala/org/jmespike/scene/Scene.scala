package org.jmespike.scene

import com.jme3.scene.Spatial

/**
 * Provides some kind of 3D environment.
 * Scenes can be nested?  E.g. sky outside landscape outside house.
 */
trait Scene {

  private var _root: Spatial = null

  def createRoot: Spatial
  
  def root: Spatial = {
    if (_root == null) _root = createRoot

    _root
  }



}

