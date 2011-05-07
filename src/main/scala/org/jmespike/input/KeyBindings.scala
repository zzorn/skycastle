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

  val forward = input(InputNames.forward, KeyInput.KEY_W)
  val back    = input(InputNames.back, KeyInput.KEY_S)
  val left    = input(InputNames.left, KeyInput.KEY_A)
  val right   = input(InputNames.right, KeyInput.KEY_D)
  val up      = input(InputNames.up, KeyInput.KEY_SPACE)
  val down    = input(InputNames.down, KeyInput.KEY_LWIN)

  val next    = input(InputNames.next, KeyInput.KEY_PGDN)
  val previous= input(InputNames.previous, KeyInput.KEY_PGUP)

  val action1 = input(InputNames.action1, KeyInput.KEY_RCONTROL)
  val action2 = input(InputNames.action2, KeyInput.KEY_RSHIFT)
  val secondary1 = input(InputNames.secondary1, KeyInput.KEY_E)
  val secondary2 = input(InputNames.secondary2, KeyInput.KEY_Q)

  val escape  = input(InputNames.escape, KeyInput.KEY_ESCAPE)
  val ok      = input(InputNames.ok, KeyInput.KEY_RETURN)

  val editor  = input(InputNames.editor, KeyInput.KEY_F1)


  def setupInput(inputManager: InputManager) {
    inputs foreach {(prop: Property[Int]) =>
      inputManager.addMapping(prop.name.name, new KeyTrigger(prop.value))
    }
  }


}