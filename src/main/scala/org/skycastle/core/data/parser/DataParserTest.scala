package org.skycastle.core.data.parser

import _root_.org.skycastle.core.data.{Value, Num}

/**
 * 
 */
object DataParserTest  {
  def main(args: Array[String]) {
    println(DataParser.parse(" 1 "))

    assert(new Num(1.0) == DataParser.parse(" 1 "))
    println( DataParser.parse(" { foo: \"bar\" } "))
    println( DataParser.parse (
    """
     {
        first: function(a:1, b) [a, math.plus(a,b)]
        second: first(1,b:true)
        a: 1
        mass: /* inline comment */ 5kg
        acc: 10.12e2m/s2
        b: 2
        c: ASmallPath
        d: ASmallPath.With.dots // Some comment
        # comment
     }

    """)) 

  }
}

