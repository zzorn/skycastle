package org.skycastle.util

import org.skycastle.entity.EntityId

/**
 * Utility mixin for a class that allows getting properties.
 *
 * Provides get methods for various types, get methods with default values and Optional results, etc.
 */
trait PropertyGetters {

  def getProperties : Map[Symbol, Any]
  def hasProperty( id : Symbol ) : Boolean = getProperties.contains( id )
  def getPropertyType( id : Symbol ) : Option[Class[_]] = {
    getProperty( id ) match {
      case Some( v ) => Some(ClassUtils.getType( v ))
      case None => None
    }
  }
  def getProperty( id : Symbol ) : Option[Any] = getProperties.get( id )

  def getProperty[T](id: Symbol, defaultValue: T) : T = {
    getProperty( id ) match {
      case Some( v ) => v.asInstanceOf[T]
      case None => defaultValue
    }
  }
  
  def getProperty[T]( id : Symbol, defaultValue : T, kind : Class[T] ) : T  = {
    val value = getProperty(id, defaultValue)
    if ( kind.isInstance(value)) value.asInstanceOf[T]
    else defaultValue
  }

  def getBoolean (id: Symbol, defaultValue: Boolean  ) : Boolean  = getProperty[Boolean] (id, defaultValue)
  def getByte    (id: Symbol, defaultValue: Byte     ) : Byte     = getProperty[Number]  (id, defaultValue).byteValue
  def getShort   (id: Symbol, defaultValue: Short    ) : Short    = getProperty[Number]  (id, defaultValue).shortValue
  def getInt     (id: Symbol, defaultValue: Int      ) : Int      = getProperty[Number]  (id, defaultValue).intValue
  def getLong    (id: Symbol, defaultValue: Long     ) : Long     = getProperty[Number]  (id, defaultValue).longValue
  def getFloat   (id: Symbol, defaultValue: Float    ) : Float    = getProperty[Number]  (id, defaultValue).floatValue
  def getDouble  (id: Symbol, defaultValue: Double   ) : Double   = getProperty[Number]  (id, defaultValue).doubleValue
  def getString  (id: Symbol, defaultValue: String   ) : String   = getProperty[String]  (id, defaultValue)
  def getSymbol  (id: Symbol, defaultValue: Symbol   ) : Symbol   = getProperty[Symbol]  (id, defaultValue)
  def getEntityId(id: Symbol, defaultValue: EntityId ) : EntityId = getProperty[EntityId](id, defaultValue)

  def getAsString(id: Symbol, defaultValue: String) : String = {
    getProperty(id) match {
      case Some(v) => v.toString
      case None => defaultValue
    }
  }

  def toKeyValueString( keyValueSeparator : String, entrySeparator : String ) : String = {
    StringUtils.mapToString( getProperties, keyValueSeparator, entrySeparator )
  }

  def toKeyValueString : String = StringUtils.mapToString( getProperties )

  override def toString = "( " + toKeyValueString( " = ", ", "  ) + " )"

  def toParameters : Parameters = new Parameters( getProperties )

}