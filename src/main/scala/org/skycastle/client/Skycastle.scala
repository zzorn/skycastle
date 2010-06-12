package org.skycastle.client

import org.skycastle.view.Screen

/**
 * Main entry point for Skycastle client.
 */
/*
TODO: Give options to
- run a server locally
  - continue playing with an existing world
  - set up a new world
- connect to a remote server
  - list the ones the player has an account on
  - get list of all running servers (through server to server networking)
 */
object Skycastle {

  def main(args: Array[String]) {
    println( "Skycastle client starting." )

    // Setup screen
    val screen = new Screen()
    screen.start

    // Get server based on user input (for now, just create a local one)

    // Allow user to select an avatar/game on the account, or create a new one

    // Enter main game loop

    // draw scene
    // get any updates
    // listen to player inputs, map controls to actions, call server with action

  }
}

