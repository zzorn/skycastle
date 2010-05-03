package org.skycastle.util

/**
 * Utility mixin for a class that allows setting properties.
 */
trait PropertySetters {

  def setProperty( property : Symbol, value : Any )

  def setProperties( properties : Map[Symbol, Any] ) : Unit =  {
    properties foreach { case (key, value) => setProperty(key, value) }
  }

  def setProperties( properties : PropertyGetters ) : Unit = setProperties( properties.getProperties )

  def setPropertiesFromKeyValueString( keyValueString : String ) : Unit = {
    setProperties( StringUtils.stringToMap( keyValueString ) )
  }

  def setPropertiesFromKeyValueString( keyValueString : String, keyValueSeparator : String, entrySeparator : String ) : Unit = {
    setProperties( StringUtils.stringToMap( keyValueString, keyValueSeparator, entrySeparator ) )
  }

}

