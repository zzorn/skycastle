package org.jmespike.input

import org.jmespike.controls.EntityControl
import com.jme3.input.controls.ActionListener
import java.lang.String
import InputNames._
import org.skycastle.util.Logging

import org.jmespike.controls.ControlConf


class PlayerSteeringConf extends ControlConf {

  // This conf could hold things like custom player mappings for this vehicle(type?)?
  // We could also keep track of steering feel here, e.g. should the throttle immediately go to max or slowly change

  def createControl(seed: Int) = new PlayerSteering()
}

/**
 * 
 */
class PlayerSteering extends EntityControl(null) with ActionListener with Logging {

  // TODO: Orientation - get from mouse?  Or pitch roll yaw with WASD?

  val forwardAxis = new SteeringAxis()
  val rightAxis   = new SteeringAxis()
  val upAxis      = new SteeringAxis()

  def onAction(name: String, isPressed: Boolean, tpf: Float) {

    withEntityControl[SteeringControl] {steering =>
      log.debug("Invoking steering entity control "+steering + " with action "+name)

      name match {
        case Up      => steering.up      = upAxis.updateIncrease(isPressed)
        case Down    => steering.up      = upAxis.updateDecrease(isPressed)
        case Right   => steering.right   = rightAxis.updateIncrease(isPressed)
        case Left    => steering.right   = rightAxis.updateDecrease(isPressed)
        case Forward => steering.forward = forwardAxis.updateIncrease(isPressed)
        case Back    => steering.forward = forwardAxis.updateDecrease(isPressed)
        case _ => // Ignore
      }
    }
  }
}

class SteeringAxis(min: Float = -1f, max: Float = 1f) {
  var value: Float = 0f
  var increaseHeld = false
  var decreaseHeld = false

  def updateIncrease(pressed: Boolean): Float = {
    increaseHeld = pressed
    updateValue()
    value
  }

  def updateDecrease(pressed: Boolean): Float = {
    decreaseHeld = pressed
    updateValue()
    value
  }

  private def updateValue() {
    value = 0f
    if (increaseHeld) value += 1f
    if (decreaseHeld) value -= 1f
  }
}


