package org.jmespike.input

import org.jmespike.controls.ControlConf

/**
 * 
 */

class SteeringConf extends ControlConf {
  def createControl(seed: Int) = new SteeringControl()
}