package org.jmespike.input

import org.jmespike.utils.Desc

/**
 * Something that provides switches that can be used to control it.
 * Switches can be added or removed on the fly.
 */
// TODO: Rename to something more intuitive
trait Switchable {

  /**
   * The switches provided by this controllable.
   */
  def switches: List[Symbol]

  /**
   * A description of the specified switch for a user.
   */
  def description(switch: Symbol): Desc = Desc(switch.name)

  /**
   * A mapping from each available switch to its description.
   */
  def descriptions: Map[Symbol, Desc] = (switches map (s => (s, description(s)))).toMap

  /**
   * Set the switch to a value.
   * The value is a number between 0 and 1, and could for example indicate
   * the degree to which the switched thing should be activated.
   * It could be controlled with a keypress (e.g. keydown = 1 and up = 0), a joystick axis, or programmatically
   * by an AI, remove player, or some script.
   *
   * Logs a warning if there was no such switch.
   */
  def setSwitch(switch: Symbol, value: Float)

  /**
   * The current value of the specified switch.
   *
   * Logs a warning and returns zero if there was no such switch.
   */
  def getSwitch(switch: Symbol): Float

  /**
   * Adds a listener that is notified when a switch is added.
   * It is passed the id of the added switch.
   */
  def addSwitchAddListener(listener: (Symbol) => Unit)

  /**
   * Adds a listener that is notified when a switch is removed.
   * It is passed the id of the removed switch.
   */
  def addSwitchRemoveListener(listener: (Symbol) => Unit)

  /**
   * Adds a listener that is notified when a switch value is changed.
   * It is passed the id of the switch and the new value.
   */
  def addSwitchChangeListener(listener: (Symbol, Float) => Unit)

  /**
   * Removes a switch add listener.
   */
  def removeSwitchAddListener(listener: (Symbol) => Unit)

  /**
   * Removes a switch remove listener.
   */
  def removeSwitchRemoveListener(listener: (Symbol) => Unit)

  /**
   * Removes a switch change listener.
   */
  def removeSwitchChangeListener(listener: (Symbol, Float) => Unit)

}


