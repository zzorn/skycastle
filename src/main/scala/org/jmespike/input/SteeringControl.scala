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

// TODO: Should the entity control also inherit Conf?  Might make learning AI:s easier, but until then we can use var:s
class SteeringControl extends EntityControl(null) with ActionListener {

  // TODO: Different steering for walker, roll-pitch-yaw spaceship, road-vehicle, etc?

  // Desired target orientation

  // Desired speed, 

  var forward = 0f
  var right = 0f
  var up = 0f

  def onAction(name: String, isPressed: Boolean, tpf: Float)
  {}
}