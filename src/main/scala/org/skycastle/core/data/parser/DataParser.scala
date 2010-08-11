package org.skycastle.core.data.parser

import _root_.org.skycastle.core.data._
import scala.util.parsing.combinator.syntactical.StdTokenParsers
import util.parsing.combinator.ImplicitConversions
import java.io.{File}
import util.parsing.input.Reader
import tools.cmd.Meta.Opt

/**
 * A simple data format language parser.
 *
 * @author Hans Haggstrom
 */
object DataParser extends StdTokenParsers  {

  def parse(sourceFile: File): Value = {
    parse( io.Source.fromFile(sourceFile).mkString, sourceFile.getPath )
  }

  def parse(source: String): Value = parse(source, "<unknown source>")

  def parse(source: String, sourceName: String): Value = {
    val tokens = new lexical.Scanner(source)
    dataFile(tokens) match {
      case s: Success[Value] => s.result
      case f: NoSuccess => throw new ParseError(f.msg + "\non line: " + f.next.pos.line + ", column " + f.next.pos.column + " of " + sourceName, null)
    }
  }


  // Setup lexer
  type Tokens = DataLexer
  val lexical = new DataLexer

  def dataFile: Parser[Value] = phrase( value )

  // Parsing data structure
  def value: Parser[Value] = bool | measure | num | fun | text | data | arr | call | link | failure( "Value expected" )

  // TODO: Add id type for longs?

  def data: Parser[Value] = "{" ~! repsep(attribute, opt( ",")) ~ "}" ^^ {case lb ~ entries ~ rb => Data(Map() ++ entries)}
  def arr: Parser[Value] = "[" ~! repsep(value, opt(",")) ~ "]" ^^ {case lb ~ entries ~ rb => Arr(entries)}
  def bool: Parser[Value] = trueValue | falseValue
  def call: Parser[Value] = (link | /* call |*/ arr | data | fun) ~ "(" ~ repsep(  parameter, ",") ~ ")" ^^ {case callee ~ lp ~ params ~ rp => Call(callee, params)}
  def fun: Parser[Value] = "function" ~! "(" ~ repsep(  parameterDeclaration, ",") ~ ")" ~ value ^^ { case c1 ~ c2 ~ params ~ c3 ~ body => Fun(params, body) }
  def num: Parser[Value] = opt("-") ~ numericLit ^^ { case n ~ s => Num(s.toDouble * (if (n == None) 1 else -1)) }
  def text: Parser[Value] = stringLit ^^ {case s => Text(s)}
  def link: Parser[Link] = opt("$") ~ rep1sep(identifier, ".") ^^ {case param ~ path => Link(param.isDefined, path)}
  def measure: Parser[Measure] = new Parser[Measure](){
    def apply(in: Reader[Elem]) = {
      in.first match {
        case m: lexical.MeasurementLit => Success(m.measure, in.rest)
        case _ => Failure("not a measure", in)
      }
    }
  }

  def identifier: Parser[Symbol] = ident ^^ {case id => Symbol(id)}

  private def attribute: Parser[Tuple2[Symbol, Value]] = identifier ~ ":" ~ value ^^ {case name ~ c ~ value => (name, value) }

  private def parameterDeclaration: Parser[Tuple2[Symbol, Option[Value]]] = identifier ~ opt( ":" ~> value) ^^ { case name ~ value => Tuple2(name, value) }
  private def parameter: Parser[Tuple2[Option[Symbol], Value]] = opt(identifier <~ ":") ~ value ^^ { case name ~ value => Tuple2(name, value)}

  def trueValue : Parser[Bool] = "true" ^^ { x => True }
  def falseValue : Parser[Bool] = "false" ^^  { x => False}

}

