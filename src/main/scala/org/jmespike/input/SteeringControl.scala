package org.jmespike.input

import org.jmespike.controls.EntityControl
import com.jme3.input.controls.ActionListener
import java.lang.String
import InputNames._

/**
 * Receives steering information from user input (via an activity)
 * or through direct setting by AIs (or remote players).
 */
// TODO: Separate the sourcing of input from a player, an AI, the network, etc, and the use of that input to control something
// Current planned path of user input is:
// Keyboard -> JME Input handling -> game Activity -> player entity -> steering control -> propulsion control -> physics control
// Instead, make EntityControl support registering listeners, and do
// Keyboard -> JME Input handling -> input distributor app state -> entity control -> player steering control -> propulsion control -> physics control
class SteeringControl extends EntityControl(null) with ActionListener {

  // Desired target orientation

  // Desired speed, 


  def onAction(name: String, isPressed: Boolean, tpf: Float) {
    name match {
      case Up =>
      case Down =>
      case _ => // Ignore
    }
  }

}