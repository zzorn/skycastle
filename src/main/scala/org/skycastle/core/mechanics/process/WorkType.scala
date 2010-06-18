package org.skycastle.core.mechanics.process

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
  
}