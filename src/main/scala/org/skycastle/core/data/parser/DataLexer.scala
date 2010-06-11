package org.skycastle.core.data.parser

import _root_.scala.util.matching.Regex
import util.parsing.combinator.ImplicitConversions
import util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.input.CharArrayReader.EofCh

import java.util.regex.Pattern
import scala.util.matching.Regex
import scala.util.parsing.input._
import scala.collection.immutable.PagedSeq

/**
 *
 *
 * @author Hans Haggstrom
 */
class DataLexer extends StdLexical with ImplicitConversions  {
  override def token: Parser[Token] =
    ( identifierChar ~ rep( identifierChar | digit )    ^^ { case first ~ rest => processIdent(first :: rest mkString "") }
/* TODO: Add later
    | measure
*/
    | number ~ letter ^^ { case n ~ l => ErrorToken("Invalid number format : " + n + l) }
    | number ^^ NumericLit
    | '\'' ~ rep( chrExcept('\'', '\n', EofCh) ) ~ '\'' ^^ { case '\'' ~ chars ~ '\'' => StringLit(chars mkString "") }
    | '\"' ~ rep( chrExcept('\"', '\n', EofCh) ) ~ '\"' ^^ { case '\"' ~ chars ~ '\"' => StringLit(chars mkString "") }
    | EofCh                                             ^^^ EOF
    | '\'' ~> failure("unclosed string literal")
    | '\"' ~> failure("unclosed string literal")
    | delim
    | failure("illegal character")
    )

  def identifierChar = letter | '_'

/*
  def measure = number ~ letter ~ rep(letter | number) ~ opt( measureDivisor )
  def measureDivisor: Parser[Token] = "/" ~ letter ~ rep(letter | number)
*/

  def number = intPart ~ opt(fracPart) ~ opt(expPart) ^^ { case i ~ f ~ e => i + optString(".", f) + optString("", e) }
  def intPart = rep1(digit)        ^^ { _ mkString ""}
  def fracPart = '.' ~> rep(digit) ^^ { _ mkString "" }
  def expPart = exponent ~ opt(sign) ~ rep1(digit) ^^ { case e ~ s ~ d => e + optString("", s) + d.mkString("") }

  private def optString[A](pre: String, a: Option[A]) = a match {
    case Some(x) => pre + x.toString
    case None => ""
  }

  def zero: Parser[String] = '0' ^^^ "0"
  def nonzero = elem("nonzero digit", d => d.isDigit && d != '0')
  def exponent = elem("exponent character", d => d == 'e' || d == 'E')
  def sign = elem("sign character", d => d == '-' || d == '+')



  delimiters ++= Set( "(", ")", "[", "]", "{", "}", ",", ".", ":", ";", "@", "=", "?", "|", "&", "%",
                      "+", "-", "*", "/", "^",
                      "<", ">", "!=", "==", "<=", ">=" )

  reserved ++= Set( "function", "true", "false", "not", "or", "and", "xor", "nand", "nor", "import" )


  // Overriding the default whitespace parser, as it failed with multiline comments.
  override def whitespace: Parser[Any] =regex(whiteSpaceRegexp)

  // Skip whitespace, java style comments, and comments starting with #
  val whiteSpaceRegexp = """((\s+)|(?:/\*(?:[^*]|(?:\*+[^*/]))*\*+/)|(?://.*)|(?:#.*))+""".r

  /** A parser that matches a regex string */
  def regex(r: Regex): Parser[String] = new Parser[String] {
    def apply(in: Input) = {
      val source = in.source
      val offset = in.offset
      val start = offset // handleWhiteSpace(source, offset)
      (r findPrefixMatchOf (source.subSequence(start, source.length))) match {
        case Some(matched) =>
          Success(source.subSequence(start, start + matched.end).toString,
                  in.drop(start + matched.end - offset))
        case None =>
          Success("", in)
      }
    }
  }

}