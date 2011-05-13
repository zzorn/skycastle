package org.jmespike.movement

import org.jmespike.conf.Conf
import org.jmespike.controls.ControlConf

/**
 * 
 */
class PropulsionConf extends ControlConf {

  def createControl(seed: Int) = new PropulsionControl(this)
  
}