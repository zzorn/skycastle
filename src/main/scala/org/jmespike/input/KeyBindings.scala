package org.jmespike.input

import com.jme3.input.controls.{KeyTrigger, ActionListener}
import com.jme3.app.Application._
import com.jme3.input.{InputManager, KeyInput}
import org.jmespike.conf.Conf
import org.scalaprops.Property

/**
 * 
 */
// TODO: Support for multiple keys for one action - list of keycodes?  Or just a set of max 3?
// TODO: Support mouse and joy triggers too - generic Trigger support
class KeyBindings extends Conf {

  private var inputs: List[Property[Int]] = Nil

  def input(name: String, key: Int): Property[Int] = {
    val prop = property(Symbol(name), key)
    inputs ::= prop
    prop
  }

  val forward = input(InputNames.Forward, KeyInput.KEY_W)
  val back    = input(InputNames.Back, KeyInput.KEY_S)
  val left    = input(InputNames.Left, KeyInput.KEY_A)
  val right   = input(InputNames.Right, KeyInput.KEY_D)
  val up      = input(InputNames.Up, KeyInput.KEY_SPACE)
  val down    = input(InputNames.Down, KeyInput.KEY_LWIN)

  val next    = input(InputNames.Next, KeyInput.KEY_PGDN)
  val previous= input(InputNames.Previous, KeyInput.KEY_PGUP)

  val action1 = input(InputNames.Action1, KeyInput.KEY_RCONTROL)
  val action2 = input(InputNames.Action2, KeyInput.KEY_RSHIFT)
  val secondary1 = input(InputNames.Secondary1, KeyInput.KEY_E)
  val secondary2 = input(InputNames.Secondary2, KeyInput.KEY_Q)

  val escape  = input(InputNames.Escape, KeyInput.KEY_ESCAPE)
  val ok      = input(InputNames.Ok, KeyInput.KEY_RETURN)

  val editor  = input(InputNames.Editor, KeyInput.KEY_F1)


  def setupInput(inputManager: InputManager) {
    inputs foreach {(prop: Property[Int]) =>
      inputManager.addMapping(prop.name.name, new KeyTrigger(prop.value))
    }
  }


}