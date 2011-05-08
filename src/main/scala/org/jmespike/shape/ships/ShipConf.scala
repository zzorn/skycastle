package org.jmespike.shape.ships

import org.jmespike.conf.Conf
import com.jme3.scene.Spatial

/**
 * Generates ship shapes.
 */
class ShipConf extends Conf {

  val core = p('core, new Core())

  // TODO Graphical style etc?


  def createModel(): Spatial = {

    // Create the model, starting by the core and adding connected parts, passing in this ShipConf to provide style information to all parts.
    core().createModel(this)

  }

}