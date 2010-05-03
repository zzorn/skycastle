package org.skycastle.entity.properties

import org.skycastle.entity.accesscontrol.{ReadRight, EditRight, Role}
import org.skycastle.util.{PropertySetters, PropertyGetters, ClassUtils}


/**
 * Provides support for properties that can be listened to, that have access control, and that can be queried.
 * <p>
 * The syntax for defining a property is:
 *
 * <pre>
 * 'propertyName :- propertyValue
 * </pre>
 *
 * Optionally property value can be followed by access control specification, listener addition, etc.
 *
 * <pre>
 * 'propertyName :- propertyValue editor 'editorRole onChange listenerFunction
 * </pre>
 *
 * The property can be assigned to a val for easier access, or just defined in a subclass constructor or method.
 *
 * <pre>
 * val propertyHandle = 'propertyName :- propertyValue
 * </pre>
 *
 * The value of a property can be changed with :=, and read directly or with .value:
 *
 * <pre>
 * propertyHandle := newValue
 * println propertyHandle
 * println propertyHandle.value
 * </pre>
 *
 *
 */
// TODO: Change creation so that invariants, roles and such are specified before :-, so that they can't be added later in other places.
// TODO: Add PropertyEditors or some such interface with add/remove property helpers
// TODO: Rename to something reflecting the typed parameters with listeners etc.  TypedProperties?  RichProperties?
// TODO: Some own exception type for illegal property accesses?
trait Properties extends PropertyGetters with PropertySetters {

  import PropertyConversions._
  
  private var myProperties : Map[Symbol, Property[_]] = Map()

  override def getProperties : Map[Symbol, Any] = myProperties.mapElements( _.value ).asInstanceOf[Map[Symbol, Any]]
  override def getProperty( name : Symbol ) : Option[Any] = {
    myProperties.get( name ) match {
      case Some(p) => Some(p.value)
      case None => None
    }
  }
  override def hasProperty( id : Symbol ) : Boolean = myProperties.contains( id )

  override def setProperty( name : Symbol, value : Any ) {
    property( name ) match {
      case Some(p : Property[Any]) => p.setValue( value )
      case None => throw new IllegalArgumentException( "No property named '"+name.name+"' found in '"+this.toString+"'." )
      case p => throw new IllegalStateException( "A property '"+p+"' named '"+name.name+"' found in '"+this.toString+"', but it was not of type Property[Any]" )
    }
  }


  def ~ (id : Symbol) : Property[Any] = myProperties( id ).asInstanceOf[Property[Any]]
  def ~+ ( id : Symbol ) : PropertyMaker = PropertyMaker( id )

/*
  // TODO: Is this a bit extreme?  We may want to use apply for something else too..
  def apply( name : Symbol ) : Property[Any] = myProperties( name ).asInstanceOf[Property[Any]]
*/

  def properties : Map[Symbol, Property[_]] = myProperties
  def property( name : Symbol ) : Option[Property[Any]] = myProperties.get( name ).asInstanceOf[Option[Property[Any]]]

  def addProperty[T]( id : Symbol, value : T ) : Property[T] = {
    addProperty( id, value, ClassUtils.getType( value ) )
  }

  def addProperty[T]( id : Symbol, value : T, kind : Class[T] ) : Property[T] = {
    if (myProperties.contains( id )) throw new IllegalArgumentException( "Can not add property, the property '"+id.name+"' already exists in 'this.toString'." )

    val property = new Property[T]( id, value, kind )
    val entry = id -> property
    myProperties = myProperties + entry 
    property
  }

  def removeProperty[T]( id : Symbol ) {
    if (myProperties.contains( id )) {
      myProperties = myProperties - id
    }
  }


  implicit def symbolToPropertyMaker( id : Symbol ) = PropertyMaker( id )

  case class PropertyMaker private[Properties] (id : Symbol) {
    def :- [T] ( value : T ) : Property[T] = addProperty( id, value )
    def :< [T] ( kind : Class[T] ) : PropertyKindMaker[T] = PropertyKindMaker[T]( id, kind )
  }

  case class PropertyKindMaker[T] private[Properties] (id : Symbol, kind : Class[T]) {
    def :- ( value : T ) : Property[T] = addProperty( id, value, kind )
  }


}




