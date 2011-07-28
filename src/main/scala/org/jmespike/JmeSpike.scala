package org.jmespike

import activity.{MainMenuActivity, SpaceGameActivity, Activity}
import scene.{TestScene, Scene}

/**
 * 
 */
object JmeSpike extends BaseGame {

  def createInitialActivity(): Activity = {
    val gameActivity = new SpaceGameActivity(loadSceneSettings("config/jmespike.json"))

    new MainMenuActivity(gameActivity, loadSceneSettings("config/blueplanets.json"))
  }

  private def loadSceneSettings(path: String): Scene = {
    log.info("Loading settings from " + path)
    var conf = assetManager.loadAsset(path).asInstanceOf[TestScene]
    if (conf == null) {
      log.warn("  Failed to load settings from "+path+", using defaults")
      conf = new TestScene
    }
    conf
  }

}