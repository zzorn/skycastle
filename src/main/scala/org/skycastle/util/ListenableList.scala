package org.skycastle.util


/**
 * List that provides listeners for addition and removal of elements, as well as validators for elements.
 */
class ListenableList[T] {

  def this(requireNonNull: Boolean, requireUnique: Boolean) {
    this()
    if (requireNonNull) addNotNullValidator()
    if (requireUnique) addUniqueValidator()
  }

  type Validator = T => String
  type Listener = T => Unit

  private var _list: List[T] = Nil
  private var validators: List[Validator] = Nil
  private var additionListeners: List[Listener] = Nil
  private var removalListeners: List[Listener] = Nil

  def addNotNullValidator() = addValidator(e => if (e == null) "it was null" else null)
  def addUniqueValidator() = addValidator(e => if (_list.contains(e)) "it was already added" else null)

  def addValidator(validator: Validator) = validators = validators ::: List(validator) // Call in order of addition
  def removeValidator(validator: Validator) = validators = validators.filterNot(v => v == validator)

  def addAdditionListener(listener: Listener) = additionListeners = additionListeners ::: List(listener)  // Listeners get called in addition order
  def removeAdditionListener(listener: Listener) = additionListeners = additionListeners.filterNot(l => l == listener)

  def addRemovalListener(listener: Listener) = removalListeners = removalListeners ::: List(listener)  // Listeners get called in addition order
  def removeRemovalListener(listener: Listener) = removalListeners = removalListeners.filterNot(l => l == listener)

  def add(element: T) {
    validators.foreach({ v =>
      val error = v(element)
      if (error != null) throw new IllegalArgumentException("Element '"+element+" can not be added to the list because " + error)
    })

    _list = _list ::: List(element)
    additionListeners.foreach(l => l(element))
  }

  def remove(element: T) {
    if (_list.contains(element)) {
      _list = _list.filterNot(e => e == element)
      removalListeners.foreach(l => l(element))
    }
  }

  def apply(): List[T] = list
  def list: List[T] = _list
}