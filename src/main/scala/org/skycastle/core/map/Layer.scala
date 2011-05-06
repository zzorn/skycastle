package org.skycastle.core.map

/**
 * 
 */
trait Layer {

  def fields: Set[Symbol]

  def field(name: Symbol): Field


}