package org.skycastle.core.game

import org.skycastle.core.entity.Facet
import org.skycastle.core.platform.persistence.Ref
import org.skycastle.util.ListenableList
import org.skycastle.core.space.Space

// TODO: Refactor to activity, where each phase is one activity, and maybe a state changing activity that morphs into a specific activity

/**
 * A game has players,
 * a setup phase for the game,
 * a setup phase for each player,
 * a gameplay phase,
 * and possibly and end phase with results and scores for each player.
 */
class Game extends Facet {

  sealed trait Phase
  case object Setup extends Phase
  case object Play extends Phase
  case object Ended extends Phase

  private var _phase: Phase = Setup
  val players = new ListenableList[Ref[Player]](true, true)
  var space: Ref[Space] = null

  def phase: Phase = _phase

  

}

