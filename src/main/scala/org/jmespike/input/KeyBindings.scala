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

  val forward = p('forward, KeyInput.KEY_W)
  val back    = p('back, KeyInput.KEY_S)
  val left    = p('left, KeyInput.KEY_A)
  val right   = p('right, KeyInput.KEY_D)
  val up      = p('up, KeyInput.KEY_SPACE)
  val down    = p('down, KeyInput.KEY_LWIN)

  val next    = p('next, KeyInput.KEY_PGDN)
  val previous= p('previous, KeyInput.KEY_PGUP)

  val action1 = p('action1, KeyInput.KEY_RCONTROL)
  val action2 = p('action2, KeyInput.KEY_RSHIFT)
  val secondary1 = p('secondary1, KeyInput.KEY_E)
  val secondary2 = p('secondary2, KeyInput.KEY_Q)

  val escape  = p('escape, KeyInput.KEY_ESCAPE)
  val ok      = p('ok, KeyInput.KEY_RETURN)

  val editor  = p('editor, KeyInput.KEY_F1)


  def setupInput(inputManager: InputManager) {

    def addMapping(p: Property[Int]) {
      val actionName = p.name.name
      inputManager.addMapping(actionName, new KeyTrigger(p.value));
    }

    addMapping(forward)
    addMapping(back)
    addMapping(left)
    addMapping(right)
    addMapping(up)
    addMapping(down)

    addMapping(next)
    addMapping(previous)

    addMapping(action1)
    addMapping(action2)
    addMapping(secondary1)
    addMapping(secondary2)

    addMapping(escape)
    addMapping(ok)

    addMapping(editor)
  }


}