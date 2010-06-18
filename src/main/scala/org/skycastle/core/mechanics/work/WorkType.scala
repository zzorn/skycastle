package org.skycastle.core.mechanics.work

/**
 * Some type of work, e.g. Hammering, Sawing, Screwing, Hacking.
 * The work is measured in units (e.g. the amount of work acomplished by one hammer hit by a seasoned practicer).
 *
 * The time and energy and tool usages vary depending on the skill level of the user,
 * the quality, properties and conditions of the tools, the required precision, etc.
 *
 * Animations and sound effects could be generated based on tool usages.
 * The usage pattern is typically a number of usages, a pause to check the progres, and repeat.
 * The force/precision required can affect e.g. sound effects if desired
 * (precision hammering tok-tok-toking, and rough hammering bang bang banging, etc...)
 * --> gives variation to visual and auditive appearance of game.
 *
 * Tools used to produce some work type may get worn / damaged depending on the work type (and used skill etc). 
 */
trait WorkType {

  /**
   * What tolerance is needed from the resulting work,
   * that is, how much the actual result may differ from the specified plan.
   * 10 cm is very rough (e.g. cut a tree), 1 cm is medium (e.g. make a table),
   * 1 mm is accurate (e.g. make locks), and 0.1 mm is very precise (e.g. make optimized engines).
   */
  def precision_m: Double

}