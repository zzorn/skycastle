package org.skycastle.core.entity


import org.skycastle.util.parameters.Parameters


/**
 * Some aspect of an entity, concentrating on a specific area of functionality.
 */
// TODO: Do we want to allow changing the entity a facet is attached to?  Sounds far fetched..
trait Facet {

  private var _entity: Entity = null
  private var _initialized: Boolean = false
  private var _parameters: Parameters = Parameters()

  def facetCategory: Symbol

  final def entity: Entity = _entity
  final def entity_=(entity: Entity) {
    _entity = entity
    onEntityChanged(_entity)
  }

  final def parameters: Parameters = _parameters

  /**
   * Initialize the facet.
   * Called before the facet is added to an entity.
   */
  final def init(parameters:  Parameters) {
    if (_initialized) throw new IllegalStateException("Init called twice - init can only be called once!")
    _initialized = true
    _parameters = parameters
    onInit()
  }

  /**
   * Called when the facet is initialized, before it is added to an entity.
   */
  protected def onInit() {}

  /**
   * Called when the facet is added to (or removed from) an entity
   */
  protected def onEntityChanged(entity: Entity) {}

  /**
   * Updates the facet if needed.
   * @param tpf the time in seconds since the last call to this method.
   */
  def update(tpf: Float) {}


}


