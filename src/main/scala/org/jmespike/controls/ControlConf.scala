package org.jmespike.controls

import org.jmespike.conf.Conf
import com.jme3.scene.control.Control

/**
 * A configuration that creates a control for an entity.
 */
trait ControlConf extends Conf {

  def createControl(seed: Int): Control

}