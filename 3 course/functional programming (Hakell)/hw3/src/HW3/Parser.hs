{-# LANGUAGE OverloadedStrings #-}

module HW3.Parser (HW3.Parser.parse) where

import Control.Monad (void)
import qualified Control.Monad.Combinators.Expr as E
import qualified Data.Bimap as B
import qualified Data.ByteString.Char8 as C
import Data.Char (chr, isAlpha, isAlphaNum)
import Data.Functor (($>))
import Data.Functor.Identity (Identity)
import Data.List (intercalate)
import Data.Scientific (Scientific)
import Data.Text (pack)
import Data.Void (Void)
import HW3.Base
import Text.Megaparsec
import Text.Megaparsec.Char (alphaNumChar, char, spaceChar, string)
import qualified Text.Megaparsec.Char.Lexer as L

type Parser = Parsec Void String

-- | @'parse' s@ takes input String @s@ and returns
-- | HiExpr if succeed, otherwise throws error from @ParseErrorBundle@
parse :: String -> Either (ParseErrorBundle String Void) HiExpr
parse = Text.Megaparsec.parse (parseExpression <* eof) ""

-- | Main parser for given input
parseExpression :: Parser HiExpr
parseExpression = E.makeExprParser parseTerms operators

-- | Parser for terms, such as primitive values, lists and dictionaries
parseTerms :: Parser HiExpr
parseTerms = do
  expr <-
    sc
      *> ( (parens parseExpression)
             <|> parseValue
             <|> parseList
             <|> parseDict
         )
        <* sc
  parseExpressionCall expr

-- | Parser for primitive values such as bool, number,
-- | function name, null, string, bytestring, action name
parseValue :: Parser HiExpr
parseValue =
  HiExprValue
    <$> ( parseBool
            <|> parseNumber
            <|> parseFunction
            <|> parseNull
            <|> parseString
            <|> parseByteString
            <|> parseAction
        )

-- | Parser for bool
parseBool :: Parser HiValue
parseBool = HiValueBool <$> (rword "true" $> True <|> rword "false" $> False)

-- | Parser for number
parseNumber :: Parser HiValue
parseNumber = HiValueNumber . toRational <$> rational
  where
    rational :: Parser Scientific
    rational = L.lexeme sc (L.signed sc L.scientific)

-- | Parser for function name
parseFunction :: Parser HiValue
parseFunction = choice $ map (\f -> try (rword f) $> HiValueFunction (functions B.! f)) (B.keys functions)

-- | Parser for null
parseNull :: Parser HiValue
parseNull = HiValueNull <$ rword "null"

-- | Parser for string
parseString :: Parser HiValue
parseString = HiValueString . pack <$> (char '"' >> manyTill L.charLiteral (char '"'))

-- | Parser for bytestring
parseByteString :: Parser HiValue
parseByteString = try $ HiValueBytes . C.pack <$> brackets (sc *> symbol "#" *> (manyTill byte "#") <* sc)
  where
    byte :: Parser Char
    byte = lookAhead (alphaNumChar *> alphaNumChar *> notFollowedBy alphaNumChar) >> chr <$> L.hexadecimal <* sc

-- | Parser for action name
parseAction :: Parser HiValue
parseAction = getA "cwd" HiActionCwd <|> getA "now" HiActionNow
  where
    getA name f = symbol name $> (HiValueAction f)

-- | Parser for list
parseList :: Parser HiExpr
parseList =
  try (lookAhead (symbol "[")) *> do
    inside <- brackets (sepBy parseExpression comma)
    parseExpressionCall $ HiExprApply (HiExprValue $ HiValueFunction HiFunList) inside

-- | Parser for dictionary
parseDict :: Parser HiExpr
parseDict = HiExprDict <$> braces (sepBy pEntry comma)
  where
    pEntry :: Parser (HiExpr, HiExpr)
    pEntry = do
      name <- parseExpression
      _ <- sc >> colon
      content <- parseExpression
      return (name, content)

-- | @`parseExpressionCall` expr@ parse expression call|query|runner for given @expr@ and
-- | returns it if succeed, otherwise returns @expr@
parseExpressionCall :: HiExpr -> Parser HiExpr
parseExpressionCall expr = lookAheadCall <|> return expr
  where
    lookAheadCall :: Parser HiExpr
    lookAheadCall =
      do
        c <- sc *> lookAhead (oneOf ['(', '.', '!'])
        case c of
          '(' -> parseFunctionCall expr
          '.' -> parseFunctionDotCall expr
          '!' -> parseRunAction expr
          _ -> empty

-- | Parser for function call
parseFunctionCall :: HiExpr -> ParsecT Void String Identity HiExpr
parseFunctionCall expr = do
  inside <- parens (sepBy parseExpression comma)
  parseExpressionCall $ HiExprApply expr inside

-- | Parser for dictionary query
parseFunctionDotCall :: HiExpr -> ParsecT Void String Identity HiExpr
parseFunctionDotCall expr =
  char '.' >> identifier >>= \ident -> parseExpressionCall $ HiExprApply expr [HiExprValue $ HiValueString $ pack ident]

-- | Parser for action runner
parseRunAction :: HiExpr -> ParsecT Void String Identity HiExpr
parseRunAction expr = symbol "!" >> parseExpressionCall (HiExprRun expr)

-- | Parsers for math operators, which are translated into @HiFun@ function calls
operators :: [[E.Operator Parser HiExpr]]
operators =
  [ [E.Prefix (symbol "/" $> \a -> HiExprApply (HiExprValue (HiValueFunction HiFunNot)) [a])],
    [ E.InfixL $ convert "*" HiFunMul,
      E.InfixL (try (char '/' *> notFollowedBy (char '=')) $> operatorToFunctionB HiFunDiv)
    ],
    [ E.InfixL $ convert "+" HiFunAdd,
      E.InfixL $ convert "-" HiFunSub
    ],
    [ E.InfixN $ convert ">=" HiFunNotLessThan,
      E.InfixN $ convert "<=" HiFunNotGreaterThan,
      E.InfixN $ convert "<" HiFunLessThan,
      E.InfixN $ convert ">" HiFunGreaterThan
    ],
    [ E.InfixN $ convert "==" HiFunEquals,
      E.InfixN $ convert "/=" HiFunNotEquals
    ],
    [E.InfixR $ convert "&&" HiFunAnd],
    [E.InfixR $ convert "||" HiFunOr]
  ]
  where
    operatorToFunctionB :: HiFun -> HiExpr -> HiExpr -> HiExpr
    operatorToFunctionB f a b = HiExprApply (HiExprValue (HiValueFunction f)) [a, b]
    convert :: String -> HiFun -> ParsecT Void String Identity (HiExpr -> HiExpr -> HiExpr)
    convert s f = symbol s $> operatorToFunctionB f

-- | Helpful parsers to construct something bigger ...

-- | Parser for alphanumeric identifier, which are used in our grammar
-- | as function/action names and queries
identifier :: Parser String
identifier = intercalate "-" <$> ((:) <$> satisfy isAlpha <*> many (satisfy isAlphaNum)) `sepBy1` char '-' <* sc

-- | Parser for skipping spaces and comments
sc :: Parser ()
sc = L.space (void spaceChar) (L.skipLineComment "//") (L.skipBlockComment "/*" "*/")

-- | Parser for symbols with space consumer
symbol :: String -> Parser String
symbol = L.symbol sc

-- | Parser expression inside parentheses ()
parens :: Parser a -> Parser a
parens = between (symbol "(") (symbol ")")

-- | Parser expression inside brackets []
brackets :: Parser a -> Parser a
brackets = between (symbol "[") (symbol "]")

-- | Parser expression inside braces {}
braces :: Parser a -> Parser a
braces = between (symbol "{") (symbol "}")

-- | Parser for comma
comma :: Parser String
comma = symbol ","

-- | Parser for colon
colon :: Parser String
colon = symbol ":"

-- | @`rword` s@ returns parser for specifically @s@ string
rword :: String -> Parser ()
rword w = string w *> notFollowedBy (alphaNumChar <|> char '-') *> sc
