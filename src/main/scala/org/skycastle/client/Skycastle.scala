package org.skycastle.client

import appearance._
import com.jme3.app.SimpleApplication
import com.jme3.asset.plugins.FileLocator
import designer.{DesignUiController, DesignView}
import org.skycastle.util.MathUtils._
import wrappers.{Vec3, ColorBean}
import com.jme3.light.DirectionalLight
import org.skycastle.util.parameters.Parameters
import org.skycastle.core.entity.types._
import org.skycastle.core.proceduraldesign.{AssemblyDesign, ComponentDesign}
import org.skycastle.util.MathUtils
import com.jme3.font.BitmapText
import com.jme3.input.KeyInput
import com.jme3.input.controls.{ActionListener, MouseButtonTrigger, KeyTrigger}
import com.jme3.collision.CollisionResults
import com.jme3.math.{Ray, ColorRGBA, Vector3f}
import com.jme3.scene.shape.Sphere
import com.jme3.scene.Geometry
import com.jme3.material.Material
import com.jme3.niftygui.NiftyJmeDisplay
import de.lessvoid.nifty.Nifty
import com.jme3.asset.AssetKey
import de.lessvoid.nifty.builder.{PanelBuilder, LayerBuilder, ScreenBuilder}
import de.lessvoid.nifty.builder.ElementBuilder.{VAlign, Align}
import de.lessvoid.nifty.tools.Color
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder
import com.jme3.texture.Texture
import com.jme3.texture.Texture.WrapMode
import com.jme3.terrain.heightmap.HillHeightMap
import com.jme3.renderer.Camera
import java.util.ArrayList
import com.jme3.bounding.BoundingBox
import com.jme3.terrain.geomipmap.{TerrainQuad, TerrainLodControl}

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

  val designView = new DesignView()
  val design = new AssemblyDesign()

  /** Setup 3D view */
  override def simpleInitApp = {

    // Register asset path
    assetManager.registerLocator("assets", classOf[FileLocator])
    //assetManager.registerLoader(classOf[JsonConfigLoader], "conf")

    // Register loadable beans
    EntityTypeLoader.registerBeanType(classOf[ColorBean], 'Color)
    EntityTypeLoader.registerBeanType(classOf[Vec3])
//    EntityTypeLoader.registerBeanType(classOf[BoxAppearance])
//    EntityTypeLoader.registerBeanType(classOf[PipeAppearance])

    // Register kinds of facets
//    FacetManager.registerFacetKind(classOf[AppearanceFacet])

    Registry.registerType('appearance, classOf[BoxAppearance])
    Registry.registerType('appearance, classOf[PipeAppearance])
    Registry.registerType('appearance, classOf[AssemblyAppearance])

    // Register types of entities
    val pipeParams = new EntityParameters()
    pipeParams.set('appearance, 'type, 'PipeAppearance)
    pipeParams.set('appearance, 'yOffset, 0)
    ArchetypeManager.addEntityType(new Archetype('pipe, pipeParams))

    // Add light to show scene
    val sun = new DirectionalLight();
    sun.setDirection(new Vector3f(-0.5f, -0.7f, -0.7f).normalizeLocal);
    rootNode.addLight(sun);
    val moon = new DirectionalLight();
    moon.setDirection(new Vector3f(0.5f, 0.2f, 0.7f).normalizeLocal);
    moon.setColor(new ColorRGBA(0.7f, 0.9f, 1.0f, 1))
    rootNode.addLight(moon);
    // rootNode.addLight(new AmbientLight()); // TODO: Update JME and get some actual ambient light

    designView.design = design
    design.parts =
      new ComponentDesign(Map('archetype -> 'pipe,
                              Symbol("appearance.x") -> 1.5f,
                              Symbol("appearance.y") -> 0.5f,
                              Symbol("appearance.z") -> 0.5f)) ::
      new ComponentDesign(Map('archetype -> 'pipe,
                              Symbol("appearance.x") -> 2.5f,
                              Symbol("appearance.y") -> 0.5f,
                              Symbol("appearance.z") -> 0.5f)) ::
      new ComponentDesign(Map('archetype -> 'pipe,
                              Symbol("appearance.yRotate") -> MathUtils.Tauf / 4,
                              Symbol("appearance.x") -> 0.5f,
                              Symbol("appearance.y") -> 0.5f,
                              Symbol("appearance.z") -> 0.5f)) ::
      Nil
    designView.generateView(getAssetManager)
    rootNode.attachChild(designView.view);

    addPipe(2, 0, 2)

    initCrossHairs()
    initKeys()
    initUi()
    initTerrain()
  }

  def initTerrain() {

    def createTerrainMaterial(a: String, b: String, c: String, h: String, alpha: String): Material = {
      // Create material from Terrain Material Definition
      val matRock = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
      // Load alpha map (for splat textures)
      matRock.setTexture("m_Alpha", assetManager.loadTexture(alpha));
      // load heightmap image (for the terrain heightmap)
      val heightMapImage = assetManager.loadTexture(h);

      // load grass texture
      val grass = assetManager.loadTexture(a);
      grass.setWrap(WrapMode.Repeat);
      matRock.setTexture("m_Tex1", grass);
      matRock.setFloat("m_Tex1Scale", 64f);

      // load dirt texture
      val dirt = assetManager.loadTexture(b);
      dirt.setWrap(WrapMode.Repeat);
      matRock.setTexture("m_Tex2", dirt);
      matRock.setFloat("m_Tex2Scale", 32f);

      // load rock texture
      val rock = assetManager.loadTexture(c);
      rock.setWrap(WrapMode.Repeat);
      matRock.setTexture("m_Tex3", rock);
      matRock.setFloat("m_Tex3Scale", 128f);
      matRock
    }

    val heightmap = new HillHeightMap(1025, 1000, 50, 100, 3);
    heightmap.load

    val terrain = new TerrainQuad("terrain", 65, 513, heightmap.getHeightMap());
    terrain.setMaterial(createTerrainMaterial("textures/twisty_grass.png", "textures/parchments.png", "textures/grey_rock.png", "textures/test_crate.png", "textures/test_crate_arrow.png" ));
    terrain.setModelBound(new BoundingBox());
    terrain.updateModelBound();
    terrain.setLocalScale(2f, 1f, 2f); // scale to make it less steep

    val cameras = new ArrayList[Camera]();
    cameras.add(getCamera());
    val control = new TerrainLodControl(terrain, cameras);
    terrain.addControl(control);

    rootNode.attachChild(terrain);
  }



  private var nifty: Nifty = null

  def initUi() {
    val niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);

    nifty = niftyDisplay.getNifty();
    nifty.loadStyleFile("nifty-default-styles.xml");
    nifty.loadControlFile("nifty-default-controls.xml");


    val toolbar = new LayerBuilder("toolbar")
    toolbar.childLayoutHorizontal
    toolbar.x("0")
    toolbar.y("0")
    toolbar.width("*")
    toolbar.height("32")

    val quitButton = new ButtonBuilder("quit", "Quit")
    quitButton.interactOnRelease("invokeCommand(Quit)")
    toolbar.control(quitButton)

    val addButton = new ButtonBuilder("add", "Add")
    addButton.interactOnRelease("invokeCommand(Add)")
    toolbar.control(addButton)

    val editScreen = new ScreenBuilder("start")
    editScreen.layer(toolbar)
    editScreen.controller(new DesignUiController)

    nifty.addScreen("edit", editScreen.build(nifty))
    nifty.gotoScreen("edit")

    // attach the nifty display to the gui view port as a processor
    guiViewPort.addProcessor(niftyDisplay)

    // disable the fly cam
//    flyCam.setEnabled(false)
    flyCam.setDragToRotate(true);

  }

  def addPipe(x: Int, y: Int, z: Int) {
    design.parts ::= new ComponentDesign(Map('archetype -> 'pipe,
                                         Symbol("appearance.x") -> (x + 0.5f),
                                         Symbol("appearance.y") -> (y + 0.5f),
                                         Symbol("appearance.z") -> (z + 0.5f)))
    rootNode.detachChild(designView.view);
    designView.generateView(getAssetManager)
    rootNode.attachChild(designView.view);
  }

  /** Declaring the "Shoot" action and mapping to its triggers. */
  private def initKeys() {
    inputManager.addMapping("Shoot",
      new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar
      new MouseButtonTrigger(0))          // trigger 2: left-button click
    inputManager.addListener(actionListener, "Shoot")
  }

  /** Defining the "Shoot" action: Determine what was hit and how to respond. */
  private val actionListener = new ActionListener() {
    @Override
    def onAction(name: String , keyPressed: Boolean, tpf: Float) = {
      if (name.equals("Shoot") && !keyPressed) {
        // 1. Reset results list.
        val results = new CollisionResults()
        // 2. Aim the ray from cam loc to cam direction.
        val ray = new Ray(cam.getLocation(), cam.getDirection())
        // 3. Collect intersections between Ray and Shootables in results list.
        rootNode.collideWith(ray, results);
        // 4. Print the results.
        //System.out.println("----- Collisions? " + results.size() + "-----");
        for (i <- 0 until results.size()) {
          // For each hit, we know distance, impact point, name of geometry.
          val collisionResult = results.getCollision(i)
          val dist = collisionResult.getDistance();
          val pt = collisionResult.getContactPoint();
          val hit = collisionResult.getGeometry().getName();

          val gridX = collisionResult.getGeometry.getUserData("gridCoordinate.x").asInstanceOf[Int]
          val gridY = collisionResult.getGeometry.getUserData("gridCoordinate.y").asInstanceOf[Int]
          val gridZ = collisionResult.getGeometry.getUserData("gridCoordinate.z").asInstanceOf[Int]
          if (gridX != null && gridY != null && gridZ != null) {
            addPipe(gridX, gridY, gridZ)
          }

          //System.out.println("* Collision #" + i);
          //System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
        }
        // 5. Use the results (we mark the hit object)
        if (results.size() > 0){
          // The closest collision point is what was truly hit:
          //val closest = results.getClosestCollision();
        }
      }
    }
  };

  /** A centred plus sign to help the player aim. */
  private def initCrossHairs() {
    //guiNode.detachAllChildren()
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt")
    val ch = new BitmapText(guiFont, false)
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2)
    ch.setText("+") // crosshairs
    ch.setLocalTranslation( // center
      settings.getWidth()/2 - guiFont.getCharSet().getRenderedSize()/3*2,
      settings.getHeight()/2 + ch.getLineHeight()/2, 0);
    guiNode.attachChild(ch);
  }

}

