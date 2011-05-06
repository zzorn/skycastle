package org.jmespike.utils

import simplex3d.math.float.functions._
import simplex3d.math.float._
import util.Random

/**
 *
 */
object RandomUtils {

  private val _rng = new XorShiftRandom()

  def vec3(spread: Vec3 = Vec3.One,
           base: Vec3 = Vec3.Zero,
           random: Random = seededRng,
           gaussian: Boolean = true): Vec3 ={
    def g: Float = random.nextGaussian().toFloat
    def l: Float = random.nextFloat * 2f - 1f

    val v = if (gaussian) Vec3(g, g, g) else Vec3(l, l, l)
    v * spread + base
  }

  def vec4(spread: Vec4 = Vec4.One,
           base: Vec4 = Vec4.Zero,
           random: Random = seededRng,
           gaussian: Boolean = true): Vec4 ={

    def g: Float = random.nextGaussian().toFloat
    def l: Float = random.nextFloat * 2f - 1f

    val v = if (gaussian) Vec4(g, g, g, g) else Vec4(l, l, l, l)
    v * spread + base
  }

  def color(spread: inVec4 = Vec4(0.5f, 0.5f, 0.5f, 0f),
            base: inVec4 = Vec4(0.5f, 0.5f, 0.5f, 1f),
            random: Random = seededRng,
            gaussian: Boolean = false): Vec4 = {
    val v: Vec4 = vec4(spread, base, random, gaussian)
    clamp(v, 0f, 1f)
  }

  def hslColor(spread: inVec4 = Vec4(0.5f, 0.5f, 0.5f, 0f),
               base: inVec4 = Vec4(0.5f, 0.5f, 0.5f, 1f),
               random: Random = seededRng,
               gaussian: Boolean = false): Vec4 = {

    // Get random vec4
    val hsl: Vec4 = vec4(spread, base, random, gaussian)

    // Roll hue to range 0..1
    val hueRolledHsl: Vec4 = Vec4(roll01(hsl.x), hsl.y, hsl.z, hsl.w)

    // Clamp values to range 0..1
    val clampedHsl = clamp(hueRolledHsl, 0f, 1f)

    // Convert from HSL to RGB color
    ColorUtils.HSLtoRGB(clampedHsl.x, clampedHsl.y, clampedHsl.z, clampedHsl.w)
  }

  /**
   * Rolls value to range 0..1.
   */
  // TODO: rewrite with mod operation, but take care negative values are handled correctly
  def roll01(v: Float): Float = {
    var t = v
    while(t < 0f) t += 1f
    while(t > 1f) t -= 1f
    t
  }

  private def seededRng: XorShiftRandom = {
    _rng.setSeed(System.currentTimeMillis)
    _rng
  }

  def randomInt: Int = seededRng.nextInt()


}