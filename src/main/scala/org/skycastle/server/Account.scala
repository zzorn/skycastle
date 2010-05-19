package org.skycastle.server

/**
 * 
 */
trait Account {

  def userName: String
  
  def avatars: List[Avatar]

  def createAvatar: AvatarCreator

  def removeAvatar(avatar: Avatar)

}

