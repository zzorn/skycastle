package org.skycastle.client

import _root_.org.skycastle.core.data.{Value, Data}

/**
 * Client side state for a session with a server / account / avatar (decide wich one makes sense - maybe avatar).
 */
trait ClientSession {

  /**

  The client provides facets(?) for various client side functionality:
   * UI components
     * Menus, quickbuttons, panels, lists, tables, specialized editors
     * HUD indicators, gauges
     * Documents that can be displayed by client side ui components
   * Control mappings
   * Server callbacks
   * Client side value prediction / iteration etc
   * Simple and sandboxed scripting?
   * 3D scene objects
     * Primitives
     * Lights, skyboxes, terrains, etc

   The client should not expose to the server in any scripts any client specific data (other than the login account etc as necessary)
   The client core creates a system menu that is not editable by the connected game, that allows loading local games, connecting to a server, etc.

   Basically the client has a similar entity system as the server, only the facets are UI components, 3D scene elements, etc?
   And persistence is memory only
   And it's also possible to update a part of the data -> mutable data structures with listeners...

   The client doesn't maybe have to have a generic data directory, but instead a similar entity storage as the server
    if data is needed for tables and such, they can be in some data facets that have own update functions.

   */

  /**
   * Updates some client side state.
   */
  def setValue(path: List[Symbol], value: Value)



}

