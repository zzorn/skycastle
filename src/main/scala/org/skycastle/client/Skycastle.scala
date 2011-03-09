package org.skycastle.client

import com.jme3.app.SimpleApplication
import com.jme3.scene.shape.Box
import com.jme3.scene.Geometry
import com.jme3.material.Material
import com.jme3.math.{ColorRGBA, Vector3f}

/**
 * Main entry point for Skycastle client.
 */
/*
TODO: Give options to
- run a server locally
  - continue playing with an existing world
  - set up a new world
- connect to a remote server
  - list the ones the player has an account on
  - get list of all running servers (through server to server networking)
 */
object Skycastle extends SimpleApplication {

  def main(args: Array[String]) {
    println( "Skycastle client starting." )

    // Get server based on user input or create a local one (for now, just create a local one)

    // Allow user to select an avatar/game on the account, or create a new one

    // TODO:
    // draw scene
    // get any updates
    // listen to player inputs, map controls to actions, call server with action

    start()
  }

  /** Setup 3D view */
  override def simpleInitApp = {

    // Spike 3D scene..
    val b = new Box(Vector3f.ZERO, 1, 1, 1);
    val geom = new Geometry("Box", b);
    val mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
    mat.setColor("m_Color", ColorRGBA.Red);
    geom.setMaterial(mat);
    rootNode.attachChild(geom);
  }
}

