package org.jmespike.activity

import com.jme3.scene.Spatial
import com.jme3.input.controls.ActionListener
import com.jme3.app.state.{AppStateManager, AbstractAppState, AppState}
import com.jme3.app.Application
import com.jme3.input.InputManager
import java.lang.String
import org.jmespike.{SceneLoader}
import org.jmespike.scene.SceneFactory
import java.util.logging.Logger
import org.skycastle.util.Logging

/**
 * Something that provides an user with some outputs and inputs.  Somewhat similar to a scene.
 * E.g. ingame, deathscene, main menu, design editor, travel on overview map, combat encounter on detailed map,
 * drive a vehicle, etc.
 *
 * TODO: Should activities be nestable?  E.g. opening settings screen and seeing game in background,
 * entering vehicle and having some controls remapped but still seeing same healthbar,
 * opening overview map in window on top of regular game?
 */
// TODO: Each activity could provide some spatial?
abstract class Activity extends AbstractAppState with Logging {

  private var application: Application = null
  private var sceneLoader: SceneLoader = null
  private var appStateManager: AppStateManager = null
//  private var _scene: Spatial = null
  private var inputManager: InputManager = null
  private var actionListeners: Map[String, (String, Boolean, Float) => Unit] = Map()
  private var activitiesToStop: List[Activity] = Nil
  private var activitiesToStart: List[Activity] = Nil
  private var loadScene = true


  def name: String = getClass.getSimpleName

  private val delegatingActionListener: ActionListener = new ActionListener{
    def onAction(name: String, isPressed: Boolean, tpf: Float) {
      logDebug("Key pressed " + name + " " + isPressed)

      actionListeners.get(name) match {
        case Some(action) => action(name, isPressed, tpf)
        case None =>
      }
    }
  }

  protected def addActionListener(actionName: String, action: (String, Boolean, Float) => Unit) {
    logDebug("Registered action "+actionName)
    actionListeners += (actionName -> action)
  }

  protected def addActionListeners(actionNames: List[String], action: (String, Boolean, Float) => Unit) {
    actionNames foreach {actionName => addActionListener(actionName, action)}
  }

  override def initialize(stateManager: AppStateManager, app: Application) {
    logDebug("Initializing activity "+name)

    super.initialize(stateManager, app)

    application = app
    sceneLoader = app.asInstanceOf[SceneLoader]
    appStateManager = stateManager
    inputManager = app.getInputManager

    handleActivation()

    logDebug("Activity "+name+" initialized")
  }

  /*
  // Provide scene to show in 3D view
  final def scene: Spatial = {
    if (_scene == null) _scene = sceneFactory.createScene
    _scene
  }
  */

  protected def sceneFactory: SceneFactory

  private def handleActivation() {
    logDebug("Activity "+name+" activated")
    actionListeners.keys foreach {actionName => inputManager.addListener(delegatingActionListener, actionName)}
    loadScene = true
    onActivated()
  }

  final override def setActive(active: Boolean) {
    if (active != isActive) {
      super.setActive(active)

      if (active) {
        handleActivation()
      }
      else {
        logDebug("Activity "+name+" deactivated")
        inputManager.removeListener(delegatingActionListener)
        onDeActivated()
      }
    }
  }

  protected def onActivated() {}
  protected def onDeActivated() {}

  // Specify what kind of mouse handling should be used (look, drag, none)
  

  // Provide menu / dialog to show

  // Provide available HUD widgets

  // Provide available actions that can be put in iconbars, menus, or bound to buttons

  // Indicate when this activity ends, and to which activity to move, if any


  override final def update(tpf: Float) {
    // If we are not about to close down, do normal update
    if (!activitiesToStop.contains(this)) {

      // Setup scene if requested
      if (loadScene) {
        loadScene = false
        sceneLoader.loadScene(sceneFactory)
      }

      onUpdate(tpf)
    }

    // Do any activity changes
    activitiesToStop foreach {stoppedActivity =>
      stoppedActivity.setActive(false)
      appStateManager.detach(stoppedActivity)
    }
    activitiesToStop = Nil

    activitiesToStart foreach {startedActivity =>
      appStateManager.attach(startedActivity)
    }
    activitiesToStart = Nil
  }

  protected def onUpdate(timePerFrame: Float) {}

  protected def changeActivityTo(activity: Activity) {
    activitiesToStop ::= this
    activitiesToStart ::= activity
  }

  protected def stopGame() {
    application.stop()
  }

}

