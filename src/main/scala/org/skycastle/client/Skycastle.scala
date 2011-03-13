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

    val innerD = 0.8f
    val outerD = 1f
    val flangeD = 1.5f
    val flangeW = 0.3f
    val len = 4f
    val openEndInsink = 2f
    val wraps = 2f

    appearance.segments =
            RoundSegment(new Vector3f(0,0,0), outerD, 2f/16, wraps, 0f) ::
            RoundSegment(new Vector3f(0,0,0), flangeD, 3f/16, wraps) ::
            RoundSegment(new Vector3f(0,0,0), flangeD, 25f/32, wraps) ::
            RoundSegment(new Vector3f(flangeW,0,0), flangeD, 26f/32, wraps) ::
            RoundSegment(new Vector3f(flangeW,0,0), outerD, 23f/32, wraps) ::
            RoundSegment(new Vector3f(len-flangeW,0,0), outerD, 6f/32, wraps) ::
            RoundSegment(new Vector3f(len-flangeW,0,0), outerD, 23f/32, wraps) ::
            RoundSegment(new Vector3f(len-flangeW,0,0), flangeD, 25f/32, wraps) ::
            RoundSegment(new Vector3f(len,0,0), flangeD, 26f/32, wraps) ::
            RoundSegment(new Vector3f(len,0,0), outerD, 28f/32, wraps) ::
            RoundSegment(new Vector3f(len,0,0), innerD, 29f/32, wraps) ::
            RoundSegment(new Vector3f(len-openEndInsink,0,0), innerD, 31f/32, wraps, 31.7f/32) ::
            Nil

    //appearance.w := 3
    
    rootNode.attachChild(appearance.createSpatial(assetManager));
  }
}

