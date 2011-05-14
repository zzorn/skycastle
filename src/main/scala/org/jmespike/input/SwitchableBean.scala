package org.jmespike.input

import org.jmespike.utils.Desc
import org.scalaprops.{Property, Bean}

/**
 * Provides a bean property based backend to a Switchable implementation.
 * Add switches with the switch function.
 */
trait SwitchableBean extends Bean with Switchable {

  private var _switches = Map[Symbol, Desc]()
  private var removeListeners = Set[(Symbol) => Unit]()
  private var addListeners = Set[(Symbol) => Unit]()
  private var changeListeners = Set[(Symbol, Float) => Unit]()

  /**
   * Shorthand for addSwitch.
   */
  protected def switch(id: Symbol, initialValue: Float = 0f, description: String = null, name: String = null, iconPath: String = null): Property[Float] = {
    addSwitch(id, initialValue, description, name, iconPath)
  }

  /**
   * Factory method for adding a switch to this bean.  Returns the created property.
   */
  def addSwitch(id: Symbol, initialValue: Float = 0f, description: String = null, name: String = null, iconPath: String = null): Property[Float] = {
    val desc = Desc(if(name != null) name else id.name, description, iconPath)
    addSwitchWithDesc(id, initialValue, desc)
  }

  /**
   * Factory method for adding a switch to this bean.  Takes a desc object instead of separate description and other strings.
   * Returns the created property.
   */
  def addSwitchWithDesc(id: Symbol, initialValue: Float = 0f, desc: Desc): Property[Float] = {
    require(!_switches.contains(id), "A switch with id '"+id.name+"' already exists")

    _switches += (id -> desc)

    val prop = addProperty(id, initialValue).onValueChange( (o, n) => changeListeners foreach (l => l(id, n)))
    // TODO: Invoke the describe of the new scalaprops on the property

    addListeners foreach (id)

    prop
  }

  /**
   * Removes the specified switch.
   */
  def removeSwitch(id: Symbol) {
    require(_switches.contains(id), "No switch with id '"+id.name+"' found")

    removeProperty(id)
    _switches -= id

    removeListeners foreach (id)
  }

  def switches = _switches.keys

  override def description(switch: Symbol) = if (_switches.contains(switch)) _switches(switch) else Desc(switch.name)
  override def descriptions = _switches

  def getSwitch(switch: Symbol): Float = {
    get(switch, 0f)
  }

  def setSwitch(switch: Symbol, value: Float) {
    if (contains(switch)) set(switch, value)
  }

  def addSwitchAddListener      (listener: (Symbol)        => Unit) {addListeners    += listener}
  def addSwitchRemoveListener   (listener: (Symbol)        => Unit) {removeListeners += listener}
  def addSwitchChangeListener   (listener: (Symbol, Float) => Unit) {changeListeners += listener}
  def removeSwitchAddListener   (listener: (Symbol)        => Unit) {addListeners    -= listener}
  def removeSwitchRemoveListener(listener: (Symbol)        => Unit) {removeListeners -= listener}
  def removeSwitchChangeListener(listener: (Symbol, Float) => Unit) {changeListeners -= listener}

}