package org.skycastle.core.data.parser

import _root_.org.skycastle.core.data._
import scala.util.parsing.combinator.syntactical.StdTokenParsers
import util.parsing.combinator.ImplicitConversions


/**
 * A simple data format language parser.
 *
 * @author Hans Haggstrom
 */
object DataParser extends StdTokenParsers  {

  def parse(s: String): ParseResult[Value] = {
    val tokens = new lexical.Scanner(s)
    value(tokens)
  }


  // Setup lexer
  type Tokens = DataLexer
  val lexical = new DataLexer

  // Parsing data structure
  def value: Parser[Value] = bool | /* ref, measure | */ num | fun | text | data | arr | call | link | failure( "Value expected" )

  // TODO: Add id type for longs?

  def data: Parser[Value] = "{" ~ repsep(attribute, opt( ",")) ~ "}" ^^ {case lb ~ entries ~ rb => Data(Map() ++ entries)}
  def arr: Parser[Value] = "[" ~ repsep(value, ",") ~ "]" ^^ {case lb ~ entries ~ rb => Arr(entries)}
  def bool: Parser[Value] = trueValue | falseValue
  def call: Parser[Value] = (link | /* call |*/ arr | data | fun) ~ "(" ~ repsep(  parameter, ",") ~ ")" ^^ {case callee ~ lp ~ params ~ rp => Call(callee, params)}
  def fun: Parser[Value] = "function" ~ "(" ~ repsep(  parameterDeclaration, ",") ~ ")" ~ value ^^ { case c1 ~ c2 ~ params ~ c3 ~ body => Fun(params, body) }
  def num: Parser[Value] = numericLit ^^ { s => Num(s.toDouble) }
  def text: Parser[Value] = stringLit ^^ {case s => Text(s)}
  def link: Parser[Link] = rep1sep(identifier, ".") ^^ {case path => Link(path)}

/*
  def measure: Parser[Measure] = measure ^^ {case m  => new Measure(m)}
*/

  def identifier: Parser[Symbol] = ident ^^ {case id => Symbol(id)}

  private def attribute: Parser[Tuple2[Symbol, Value]] = identifier ~ ":" ~ value ^^ {case name ~ c ~ value => (name, value) }

  private def parameterDeclaration: Parser[Tuple2[Symbol, Option[Value]]] = identifier ~ opt( ":" ~> value) ^^ { case name ~ value => Tuple2(name, value) }
  private def parameter: Parser[Tuple2[Option[Symbol], Value]] = opt(identifier <~ ":") ~ value ^^ { case name ~ value => Tuple2(name, value)}

  def trueValue : Parser[Bool] = "true" ^^ { x => True }
  def falseValue : Parser[Bool] = "false" ^^  { x => False}

}

