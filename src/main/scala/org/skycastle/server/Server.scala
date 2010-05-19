package org.skycastle

import _root_.java.util.concurrent.Future
import server.Account

/**
 * Facade to a server on client side.
 */
trait Server {

  def name: String
  
  def login(userName: String, password: Array[Char]): Future[Account]
  
}

