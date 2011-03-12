package org.skycastle.core.entity


import org.scalaprops.Bean
import com.jme3.math.ColorRGBA


/**
 * Some aspect of an entity, concentrating on a specific area of functionality.
 */
trait Facet extends Bean {

  private var _entity: Entity = null

  def entity_=(e: Entity) = _entity = e
  def entity: Entity = _entity

  private var _instanceParameters: Map[Symbol, Any] = Map()
  private var _entityParameters: Map[Symbol, Any] = Map()
  private var _facetParameters: Map[Symbol, Any] = Map()

  def instanceParameters: Map[Symbol, Any] = _instanceParameters
  def entityParameters: Map[Symbol, Any] = _entityParameters
  def facetParameters: Map[Symbol, Any] = _facetParameters

  def stringParam(params: Map[Symbol, Any], name: Symbol, default: String): String = {
    params.getOrElse(name, default).toString
  }

  def colorParam(params: Map[Symbol, Any], name: Symbol, default: ColorRGBA): ColorRGBA = {
    if (!params.contains(name)) default else {
      val c: Map[Symbol, Any] = params(name).asInstanceOf[Map[Symbol, Any]]
      new ColorRGBA(floatParam(c, 'r, 0f), floatParam(c, 'g, 0f), floatParam(c, 'b, 0f), floatParam(c, 'a, 1f))
    }
  }

  def symbolParam(params: Map[Symbol, Any], name: Symbol, default: Symbol): Symbol = {
    Symbol(stringParam(params, name, default.name))
  }

  def floatParam(params: Map[Symbol, Any], name: Symbol, default: Float): Float = {
    params.getOrElse(name, default).asInstanceOf[Number].floatValue
  }

  def intParam(params: Map[Symbol, Any], name: Symbol, default: Int): Int = {
    params.getOrElse(name, default).asInstanceOf[Int]
  }

  /**
   * Initialize the facet.
   * Called before the facet is added to an entity.
   */
  def init(instanceParameters:  Map[Symbol, Any],
           entityParameters:  Map[Symbol, Any],
           facetParameters:  Map[Symbol, Any]) {
    _instanceParameters = instanceParameters
    _entityParameters = entityParameters
    _facetParameters = facetParameters
  }

  /**
   * Updates the facet if needed.
   * @param tpf the time in seconds since the last call to this method.
   */
  def update(tpf: Float) {}

  protected def getFacet[T <: Facet](implicit m: Manifest[T]): Option[T] = {
    if (entity == null) None
    else entity.getFacet[T](m)
  }

  protected def facet[T <: Facet](implicit m: Manifest[T]): T = {
    if (entity == null) throw new IllegalArgumentException("No facet of type " + m.erasure.getName + " found, because the entity is not specified")
    else entity.facet[T](m)
  }

  protected def withFacet[T <: Facet](op: T => Unit)(implicit m: Manifest[T]) = {
    entity.withFacet[T](op)(m)
  }

}


