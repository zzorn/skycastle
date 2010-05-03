package org.skycastle.util

import _root_.org.skycastle.entity.entitycontainer.EntityContainer

object SimpleProperties {

  def apply( elements: (Symbol, Any)* ) : SimpleProperties = new SimpleProperties( Map.empty ++ elements )

}


/**
 * A class that provides simple mutable Property support.
 *
 * Doesn't separately store property types, or enforce property creation before assignment.
 *
 * @author Hans Haggstrom
 */
case class SimpleProperties() extends PropertyGetters with PropertySetters {

  def this( parameters : Parameters ) {
    this()
    properties = parameters.entries
  }

  def this( elements : Map[Symbol, Any] ) {
    this()
    properties = elements
  }

  def this( elements: (Symbol, Any)* ) {
    this()
    properties = Map.empty ++ elements
  }

  private var properties : Map[Symbol, Any] = Map()

  def clearProperties = properties = Map()
  def getProperties : Map[Symbol, Any] = properties
  def setProperty(id: Symbol, value: Any) = properties = properties + (id -> value)

}

