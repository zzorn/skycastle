package org.skycastle.shared.data

/**
 * Stores named data objects.
 */
trait MapValue extends Value {

  def entry(name: Symbol): Value
  
}

