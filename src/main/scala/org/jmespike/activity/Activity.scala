package org.jmespike.activity

/**
 * Something that provides an user with some outputs and inputs.  Somewhat similar to a scene.
 * E.g. ingame, deathscene, main menu, design editor, travel on overview map, combat encounter on detailed map,
 * drive a vehicle, etc.
 *
 * TODO: Should activities be nestable?  E.g. opening settings screen and seeing game in background,
 * entering vehicle and having some controls remapped but still seeing same healthbar,
 * opening overview map in window on top of regular game?
 */
trait Activity {

  // Provide available basic control mappings (movement keys, action keys, mouse, joy axes)

  // Provide scene to show in 3D view

  // Specify what kind of mouse handling should be used (look, drag, none)

  // Provide menu / dialog to show

  // Provide available HUD widgets

  // Provide available actions that can be put in iconbars, menus, or bound to buttons

  // Indicate when this activity ends, and to which activity to move, if any

}

