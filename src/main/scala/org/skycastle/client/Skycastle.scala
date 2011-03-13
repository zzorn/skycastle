package org.skycastle.client

import appearance.{LatheAppearance, BoxAppearance}
import com.jme3.app.SimpleApplication
import com.jme3.scene.shape.Box
import com.jme3.scene.Geometry
import com.jme3.material.Material
import com.jme3.asset.plugins.FileLocator
import org.skycastle.util.mesh.RoundSegment
import com.jme3.math.{Quaternion, ColorRGBA, Vector3f}
import org.skycastle.util.MathUtils._

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

    assetManager.registerLocator("assets", classOf[FileLocator])
    //assetManager.registerLoader(classOf[JsonConfigLoader], "conf")

    val appearance = new LatheAppearance()
    appearance.segments =
            RoundSegment(new Vector3f(0,0,0.2f), 1f, 0.1f, 0f) ::
            RoundSegment(new Vector3f(1,0,0), 2f, 0.25f, direction = new Quaternion(Array[Float](0, Tauf/12, 0))) ::
            RoundSegment(new Vector3f(2,0,0), 2.2f, 0.5f) ::
            RoundSegment(new Vector3f(3,0,0), 2f, 0.75f) ::
            RoundSegment(new Vector3f(4,0.2f,0), 1f, 0.9f, 1f) :: Nil

    //appearance.w := 3
    
    rootNode.attachChild(appearance.createSpatial(assetManager));
  }
}

