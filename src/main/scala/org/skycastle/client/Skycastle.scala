package org.skycastle.client

import appearance._
import com.jme3.app.SimpleApplication
import com.jme3.scene.shape.Box
import com.jme3.scene.Geometry
import com.jme3.material.Material
import com.jme3.asset.plugins.FileLocator
import designer.DesignView
import org.skycastle.util.mesh.RoundSegment
import com.jme3.math.{Quaternion, ColorRGBA, Vector3f}
import org.skycastle.util.MathUtils._
import wrappers.{Vec3, ColorBean}
import com.jme3.light.DirectionalLight
import org.skycastle.util.parameters.Parameters
import org.skycastle.core.entity.types._
import org.skycastle.core.design.{AssemblyDesign, ComponentDesign}

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

    // Register asset path
    assetManager.registerLocator("assets", classOf[FileLocator])
    //assetManager.registerLoader(classOf[JsonConfigLoader], "conf")

    // Register loadable beans
    EntityTypeLoader.registerBeanType(classOf[ColorBean], 'Color)
    EntityTypeLoader.registerBeanType(classOf[Vec3])
    EntityTypeLoader.registerBeanType(classOf[BoxAppearance])
    EntityTypeLoader.registerBeanType(classOf[PipeAppearance])

    // Register kinds of facets
    FacetManager.registerFacetKind(classOf[PipeAppearance])
    FacetManager.registerFacetKind(classOf[BoxAppearance])

    // Register types of entities
    EntityTypeManager.addEntityType(new EntityType(
      'pipe,
      Parameters(),
      List(new FacetType('PipeAppearance))))

    // Add light to show scene
    val sun = new DirectionalLight();
    sun.setDirection(new Vector3f(-0.5f, -0.7f, -0.7f).normalizeLocal);
    rootNode.addLight(sun);
    val moon = new DirectionalLight();
    moon.setDirection(new Vector3f(0.5f, 0.2f, 0.7f).normalizeLocal);
    moon.setColor(new ColorRGBA(0.7f, 0.9f, 1.0f, 1))
    rootNode.addLight(moon);
    // rootNode.addLight(new AmbientLight()); // TODO: Update JME and get some actual ambient light

    val designView = new DesignView()
    val design = new AssemblyDesign()
    design.parts =
      new ComponentDesign(Map('entityType -> 'pipe)) ::
      new ComponentDesign(Map('entityType -> 'pipe, 'x -> 3)) ::
      Nil
    designView.design = design
    designView.generateView(getAssetManager)

    rootNode.attachChild(designView.view);

    // Test appearance
//    val appearance = new PipeAppearance()
//    rootNode.attachChild(appearance.createSpatial(assetManager));
  }
}

