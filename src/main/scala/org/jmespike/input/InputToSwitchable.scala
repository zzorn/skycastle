package org.jmespike.input

import com.jme3.input.controls.{ActionListener, AnalogListener}
import java.lang.String

/**
 * A mapping from input action keypresses, joyaxes, and mouse to a set of switches provided by some specific type of switchable.
 */
class InputToSwitchable extends ActionListener with AnalogListener{

  // Given input action, update a switch status


  def onAction(name: String, isPressed: Boolean, tpf: Float) {
    
  }

  def onAnalog(name: String, value: Float, tpf: Float) {
    
  }

}

