package org.jmespike.activity

import com.jme3.scene.Spatial
import com.jme3.input.controls.ActionListener
import com.jme3.app.state.{AppStateManager, AbstractAppState, AppState}
import com.jme3.app.Application
import com.jme3.input.InputManager
import java.lang.String
import org.jmespike.{SceneDisplay}
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
abstract class Activity extends AbstractAppState with Logging {

  private var application: Application = null
  private var sceneDisplay: SceneDisplay = null
  private var appStateManager: AppStateManager = null
  private var inputManager: InputManager = null
  private var actionListeners: Map[String, (String, Boolean, Float) => Unit] = Map()

  private var activitiesToStop: List[Activity] = Nil
  private var activitiesToStart: List[Activity] = Nil
  
  private var scene: Spatial = null
  private var sceneCreated = false

  private var activationChanged = true

  private val delegatingActionListener: ActionListener = new ActionListener{
    def onAction(name: String, isPressed: Boolean, tpf: Float) {
      logDebug("Key pressed " + name + " " + isPressed)

      actionListeners.get(name) match {
        case Some(action) => action(name, isPressed, tpf)
        case None => 
      }
    }
  }

  /**
   * The name of this activity
   */
  def name: String = getClass.getSimpleName

  /**
   * Should create the visual scene presented by this activity.  If it has no visible scene, just return null.
   */
  protected def createScene: Spatial


  /**
   * Called during the update phase before onUpdate if this activity was activated
   */
  protected def onActivated() {}

  /**
   * Called if this activity was deactivated
   */
  protected def onDeactivated() {}


  /**
   * Called each update round while the activity is active.
   */
  protected def onUpdate(timePerFrame: Float) {}



  /**
   * Adds a listener to the specific action.
   * It will be invoked when the specified action are activated.
   */
  protected def when(actionName: String)(action:  => Unit) {
    addActionListener(actionName, {(name, pressed, tpf) => if (pressed) (action) })
  }

  /**
   * Adds a listener to the specific actions.
   * It will be invoked when the specified actions are activated.
   */
  protected def whenAction(actionNames: String * )(action:  => Unit) {
    addActionListeners(actionNames.toList, {(name, pressed, tpf) => if (pressed) (action) } )
  }

  /**
   * Adds a listener to the specific action.
   * It will be invoked with the parameters action id, key pressed, and timePerFrame.
   */
  protected def addActionListener(actionName: String, action: (String, Boolean, Float) => Unit) {
    logDebug("Registered action "+actionName)
    actionListeners += (actionName -> action)
  }

  /**
   * Adds a listener to several actions.
   * It will be invoked with the parameters action id, key pressed, and timePerFrame.
   */
  protected def addActionListeners(actionNames: List[String], action: (String, Boolean, Float) => Unit) {
    actionNames foreach {actionName => addActionListener(actionName, action)}
  }

  /**
   * Stops this activity and starts the specified one.
   */
  protected final def changeActivityTo(activity: Activity) {
    activitiesToStop ::= this
    activitiesToStart ::= activity
  }

  /**
   * Stops and exits the game immediately.
   */
  protected def stopGame() {
    application.stop()
  }

  // Specify what kind of mouse handling should be used (look, drag, none)
  

  // Provide menu / dialog to show

  // Provide available HUD widgets

  // Provide available actions that can be put in iconbars, menus, or bound to buttons

  // Indicate when this activity ends, and to which activity to move, if any


  final override def setActive(active: Boolean) {
    if (active != isActive) {
      super.setActive(active)

      if (active) activationChanged = true
      else handleDeactivation()
    }
  }

  final override def update(tpf: Float) {

    // Check if we just got activated
    if (activationChanged) {
      activationChanged = false
      handleActivation()
    }

    // Do normal update
    onUpdate(tpf)

    // Stop activities queued to stop
    activitiesToStop foreach {stoppedActivity =>
      stoppedActivity.setActive(false)
      appStateManager.detach(stoppedActivity)
    }
    activitiesToStop = Nil

    // Start activities queued to start
    activitiesToStart foreach {startedActivity =>
      appStateManager.attach(startedActivity)
    }
    activitiesToStart = Nil
  }

  override def initialize(stateManager: AppStateManager, app: Application) {
    logInfo("Initializing activity "+name)

    super.initialize(stateManager, app)

    application = app
    sceneDisplay = app.asInstanceOf[SceneDisplay]
    appStateManager = stateManager
    inputManager = app.getInputManager

    sceneCreated = false
    scene = null
    activationChanged = true

    logInfo("Activity "+name+" initialized")
  }



  private def handleActivation() {
    logInfo("Activity " + name + " activated")

    // Start listening to input
    actionListeners.keys foreach
    {
      actionName => inputManager.addListener(delegatingActionListener, actionName)
    }

    // Create scene
    if (!sceneCreated) {
      scene = createScene
      sceneCreated = true
    }

    // Show scene
    if (scene != null) sceneDisplay.addScene(scene)

    // Notify descendant class
    onActivated()
  }

  private def handleDeactivation() {
    logDebug("Activity " + name + " deactivated")

    // Stop listening to input
    inputManager.removeListener(delegatingActionListener)

    // Remove scene from display
    if (scene != null) sceneDisplay.removeScene(scene)

    // TODO: Should we also destroy the scene?  In that case, we'd need to de-activate each entity,
    // as they might be listening to things and thus not get garbage collected
    // TODO: We should probably separate simulations from activities, where simulations can be more persistent,
    // and could also be supplied wih data and updates e.g. over a network, or loaded / saved to disk
    // This would imply that Activities are some kind of Views or sessions

    // Notify descendant class
    onDeactivated()
  }


}

