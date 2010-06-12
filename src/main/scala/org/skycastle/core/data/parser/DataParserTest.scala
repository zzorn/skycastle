package org.skycastle.core.data.parser

import _root_.org.scalatest.matchers.ShouldMatchers
import _root_.org.scalatest.Spec
import _root_.org.skycastle.core.data.{Value, Num}

/**
 * 
 */
object DataParserTest  {
  def main(args: Array[String]) {
    println(DataParser.parse(" 1 ").get)

    assert(new Num(1.0) == DataParser.parse(" 1 ").get)
    println( DataParser.parse(" { foo: \"bar\" } ").get)
    println( DataParser.parse (
    """
     {
        first: function(a:1, b) [a, math.plus(a,b)]
        second: first(1,b:true)
        a: 1
        mass: 5kg
        acc: 10.12e2m/s2
        b: 2
     }

    """) match {
      case f: DataParser.Failure => f.msg
      case s: DataParser.Success[Value] => s.get
    } )

  }
}

