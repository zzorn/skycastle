package org.skycastle.client

import _root_.com.ardor3d.example.ExampleBase

/**
 * Main entry point for Skycastle client.
 */
object Skycastle {

  def main(args: Array[String]) {
    print( "Skycastle client starting." )

    ExampleBase.start(classOf[MainUi])
  }

  
}

