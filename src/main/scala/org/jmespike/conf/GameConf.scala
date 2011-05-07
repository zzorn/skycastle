package org.jmespike.conf

import org.jmespike.input.KeyBindings
import org.jmespike.scene.TestScene

/**
 * 
 */
// TODO: separate game options and game content
class GameConf extends Conf {

  val gameOptions = p('gameOptions, new GameOptions)

  val startScene = p('startScene, new TestScene)

}
