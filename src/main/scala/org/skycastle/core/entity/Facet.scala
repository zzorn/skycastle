package org.skycastle.core.entity


import org.scalaprops.Bean
import org.skycastle.util.Parameters


/**
 * Some aspect of an entity, concentrating on a specific area of functionality.
 */
trait Facet extends Bean {

  private var _entity: Entity = null

  def entity_=(e: Entity) = _entity = e
  def entity: Entity = _entity

  private var _instanceParameters: Parameters = Parameters()
  private var _entityParameters: Parameters = Parameters()
  private var _facetParameters: Parameters = Parameters()

  def instanceParameters: Parameters = _instanceParameters
  def entityParameters: Parameters = _entityParameters
  def facetParameters: Parameters = _facetParameters

  /**
   * Initialize the facet.
   * Called before the facet is added to an entity.
   */
  def init(instanceParameters:  Parameters,
           entityParameters:  Parameters,
           facetParameters:  Parameters) {
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


