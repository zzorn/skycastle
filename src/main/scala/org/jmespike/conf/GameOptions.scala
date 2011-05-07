package org.jmespike.conf

import org.jmespike.input.KeyBindings

/**
 * 
 */

class GameOptions extends Conf {

  val keyBindings = p('keyBindings, new KeyBindings)

}