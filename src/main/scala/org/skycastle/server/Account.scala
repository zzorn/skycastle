package org.skycastle.server

/**
 * Client side interface to an account.
 */
trait Account {

  def userName: String
  
  def avatars: List[Avatar]

  def createAvatar: AvatarCreator

  def removeAvatar(avatar: Avatar)

}

