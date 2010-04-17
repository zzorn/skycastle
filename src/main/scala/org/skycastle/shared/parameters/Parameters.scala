package org.skycastle.util


import java.io.Serializable

object Parameters {

  def apply (  elems: (Symbol, Any)*) = {
    if (elems.length == 0) empty
    else new Parameters( Map.empty ++ elems )
  }

  val empty = new Parameters( Map.empty )

  val KEY_VALUE_SEPARATOR = '='
  val ENTRY_SEPARATOR = '\n'

  /**
   * Creates a Parameters object from a string containing key = value entries separated by the equals sign,
   * and where the entries are separated by newlines.
   */
  def fromKeyValueString( keyValueList : String ) : Parameters = {
    new Parameters( StringUtils.stringToMap( keyValueList, ""+KEY_VALUE_SEPARATOR, ""+ENTRY_SEPARATOR ) )
  }


}

/**
 * An immutable set of named properties.
 * 
 * NOTE: All parameter contents should be serializable
 *
 * @author Hans Haggstrom
 */
@serializable
@SerialVersionUID( 1 )
final case class Parameters(val entries : Map[Symbol, Any]) extends PropertyGetters {

  def getProperties = entries

  def ++ ( otherParameters : Parameters ) : Parameters = {
    val newEntries = getProperties ++ otherParameters.getProperties
    Parameters( newEntries )
  }

  def + ( entry : (Symbol, Any) ) : Parameters = {
    val newEntries = getProperties + entry
    Parameters( newEntries )
  }

  def add( key : Symbol, value : Any  ) : Parameters = {
    val entry = (key, value)
    this + entry
  }

}