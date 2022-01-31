grammar Grammar;

ABSTRACT : 'abstract';
ASSERT   : 'assert';

BOOLEAN  : 'boolean';
BREAK    : 'break';
BYTE     : 'byte';

CASE     : 'case';
CATCH    : 'catch';
CHAR     : 'char';
CLASS    : 'class';
CONST    : 'const';
CONTINUE : 'continue';

DEFAULT : 'default';
DO      : 'do';
DOUBLE  : 'double';

ELSE    : 'else';
ENUM    : 'enum';
EXTENDS : 'extends';

FINAL   : 'final';
FINALLY : 'finally';
FLOAT   : 'float';
FOR     : 'for';

IF      : 'if';
GOTO    : 'goto';

IMPLEMENTS : 'implements';
IMPORT     : 'import';
INSTANCEOF : 'instanceof';
INT        : 'int';
INTERFACE  : 'interface';

LONG : 'long';

MODULE : 'module';

NATIVE : 'native';
NEW    : 'new';
NULL    : 'null';

PACKAGE   : 'package';
PRIVATE   : 'private';
PROTECTED : 'protected';
PUBLIC    : 'public';

RETURN : 'return';

SHORT        : 'short';
STATIC       : 'static';
SUPER        : 'super';
SWITCH       : 'switch';
SYNCHRONIZED : 'synchronized';

THIS      : 'this';
THROW     : 'throw';
THROWS    : 'throws';
TO        : 'to';
TRANSIENT : 'transient';
TRY       : 'try';

VOID     : 'void';
VOLATILE : 'volatile';

WHILE    : 'while';

TYPE : 'Integer' | 'Double' | 'String' | INT | DOUBLE | BOOLEAN;
INTEGER_VALUE   : MINUS? [0-9]+;
DOUBLE_VALUE  : INTEGER_VALUE DOT [0-9]*;
BOOLEAN_VALUE : 'true' | 'false';
STRING_VALUE : QUOT .*? QUOT | QUOT_TWO .*? QUOT_TWO;
CLASS_IDENTIFIER : [A-Z] [a-zA-Z]*;
IDENTIFIER : [a-zA-Z] [a-zA-Z0-9]*;

WS : [ \t\r\n]+ -> skip;

QUOT      : '\'';
QUOT_TWO  : '"';
DOT       : '.';
COMMA     : ',';
LPAREN    : '(';
RPAREN    : ')';
LBRACE    : '{';
RBRACE    : '}';
LSQUARE   : '[';
RSQUARE   : ']';
COLON     : ':';
SEMICOLON : ';';
ASSIGN    : '=';
ARROW     : '->';

PLUS   : '+';
MINUS  : '-';
DIV    : '/';
MUL    : '*';
MOD    : '%';
INC    : '++';
DEC    : '--';
BITAND : '&';
BITOR  : '|';

OR   : BITOR BITOR;
AND  : BITAND BITAND;
LT   : '<';
GT   : '>';
LE   : LT ASSIGN;
GE   : GT ASSIGN;
EQ   : ASSIGN ASSIGN;
NOT_EQ   : NOT ASSIGN;
NOT  : '!';
Q_MARK  : '?';


compilationUnit:	packageDeclaration? importDeclaration* classDeclaration EOF;

primitive
    :   INTEGER_VALUE
    |   DOUBLE_VALUE
    |   BOOLEAN_VALUE
    |   STRING_VALUE
    |   NULL
    ;

primitiveType
    :	BYTE
	|	SHORT
	|	INT
	|	LONG
	|	CHAR
	|   FLOAT
	|   DOUBLE
	|	BOOLEAN
	;

referenceType
	:	classType
	|	variableType
	|	arrayType
	;

classType:	CLASS_IDENTIFIER argumentsType? (DOT CLASS_IDENTIFIER argumentsType?)*;

variableType : TYPE | CLASS_IDENTIFIER;

arrayType
	:	primitiveType brackets_square
	|	classType brackets_square
	|	variableType brackets_square
	;

brackets_square: (LSQUARE RSQUARE)+;

typeParameter: IDENTIFIER typeBound?;

typeBound
	:	EXTENDS variableType
	|	EXTENDS classType
	;

argumentsType:	LT typeArgumentList GT;

typeArgumentList:	typeArgument* (COMMA typeArgument)*;

typeArgument
	:	referenceType
	|	wildcard
	;

wildcard:	IDENTIFIER wildcardBounds?;

wildcardBounds
	:	EXTENDS referenceType
	|	SUPER referenceType
	;

string_dot_splitted
	:	(IDENTIFIER | CLASS_IDENTIFIER)
	|	string_dot_splitted DOT (IDENTIFIER | CLASS_IDENTIFIER)
	;

argumentList:	expression (COMMA expression)*;

methodName:	IDENTIFIER;

packageDeclaration: PACKAGE string_dot_splitted SEMICOLON;

importDeclaration:	IMPORT STATIC? string_dot_splitted (DOT MUL)? SEMICOLON;

classDeclaration:	classModifier* CLASS CLASS_IDENTIFIER typeParameters? superclass? superinterfaces? classBody;

classModifier
	:	PUBLIC
	|	PROTECTED
	|	PRIVATE
	|	ABSTRACT
	|	STATIC
	|	FINAL
	;

typeParameters:	LT typeParameterList GT;

typeArgumentsOrEmpty
	:	typeParameters
	|	LT GT
	;

typeParameterList:	typeParameter (COMMA typeParameter)*;

superclass:	EXTENDS classType;

superinterfaces: IMPLEMENTS classTypeList;

classTypeList:	classType (COMMA classType)*;

classBody:	LBRACE classMemberDeclaration* RBRACE;

classMemberDeclaration
	:	fieldDeclaration
	|	methodDeclaration
	|   constructorDeclaration
	|	classDeclaration
	;

fieldDeclaration:  fieldModifier*  (primitiveType | referenceType) variableDeclaratorList SEMICOLON;

fieldModifier
	:	PUBLIC
	|	PROTECTED
	|	PRIVATE
	|	TRANSIENT
	|	VOLATILE
	|   STATIC
	|   FINAL
	;

variableDeclaratorList:	variableDeclarator (COMMA variableDeclarator)*;

variableDeclarator:	variableDeclaratorId (ASSIGN variableInitializer)?;

variableDeclaratorId: IDENTIFIER brackets_square?;

variableInitializer
	:	primitive
	|   classInstanceCreationExpression
	|	arrayInitializer
	;

methodDeclaration:	methodModifier* methodHeader methodBody;

methodModifier
	:	PUBLIC
	|	PROTECTED
	|	PRIVATE
	|	ABSTRACT
	|	STATIC
	|	FINAL
	|	SYNCHRONIZED
	|	NATIVE
	;

methodHeader
	:	result methodDeclarator throws_?
	|	typeParameters result methodDeclarator throws_?
	;

result
	:	primitiveType
	|   classType
	|   TYPE
	|	VOID
	;

methodDeclarator:	IDENTIFIER LPAREN formalParameterList? RPAREN brackets_square?;

formalParameterList:    formalParameter (COMMA formalParameter)*;


formalParameter
	:	variableModifier* (primitiveType | referenceType) variableDeclaratorId
	;

variableModifier:	FINAL;

throws_:	THROWS exceptionTypeList;

exceptionTypeList:	exceptionType (COMMA exceptionType)*;

exceptionType
	:	classType
	|	variableType
	;

methodBody
	:	block
	|	SEMICOLON
	;

constructorDeclaration:	constructorModifier* constructorDeclarator throws_? constructorBody;

constructorModifier
	:	PUBLIC
	|	PROTECTED
	|	PRIVATE
	;

constructorDeclarator:	typeParameters? CLASS_IDENTIFIER LPAREN formalParameterList? RPAREN;

constructorBody:    LBRACE explicitConstructorInvocation? statement* RBRACE;

explicitConstructorInvocation
	:	argumentsType? (THIS | SUPER) LPAREN argumentList? RPAREN SEMICOLON
	;

arrayInitializer:	LBRACE (variableInitializerList COMMA?)? RBRACE;

variableInitializerList:	variableInitializer (COMMA variableInitializer)*;

block:	LBRACE statement* RBRACE;

localVariableDeclaration:	variableModifier* (primitiveType | referenceType) variableDeclaratorList SEMICOLON;

statement
    :	statementWithoutTrailingSubstatement
	|	ifStatement
	|	whileStatement
	|	forStatement
	|   localVariableDeclaration
	|   assignment
	;


statementWithoutTrailingSubstatement
	:	expressionStatement
	|	breakStatement
	|	continueStatement
	|	returnStatement
	;

expressionStatement:	statementExpression SEMICOLON;

statementExpression
	:	assignment
	|	methodInvocation
	|	classInstanceCreationExpression
	;

ifStatement:   IF LPAREN expression RPAREN ((block (ELSE block)?) | (statement (ELSE statement)?));

whileStatement:	WHILE LPAREN expression RPAREN (block | statement | SEMICOLON);

forStatement:	FOR LPAREN (localVariableDeclaration | SEMICOLON) expression? SEMICOLON incOrDecExpression? RPAREN block;

breakStatement:	BREAK SEMICOLON;

continueStatement:	CONTINUE SEMICOLON;

returnStatement:  RETURN expression? SEMICOLON;

expression
	:	primitive
	|   conditionalExpression
	|   classInstanceCreationExpression
	;

assignment
    :	(TYPE | primitiveType | referenceType)? IDENTIFIER assignmentOperator expression
    |   incOrDecExpression;

assignmentOperator
	:	ASSIGN
	|	MUL ASSIGN
	|	DIV ASSIGN
	|	MOD ASSIGN
	|	PLUS ASSIGN
	|	MINUS ASSIGN
	;

conditionalExpression
	:	conditionalOrExpression
	|	conditionalExpression Q_MARK expression COMMA conditionalOrExpression
	;

conditionalOrExpression
	:	conditionalAndExpression
	|	conditionalOrExpression OR conditionalAndExpression
	;

conditionalAndExpression
    : relationalExpression
    | conditionalAndExpression AND relationalExpression;

relationalExpression
    :   additiveExpression
	|	relationalExpression (LT | GT | LE | GE | EQ | NOT_EQ) additiveExpression
	;

additiveExpression
	:	multiplicativeExpression
	|	additiveExpression (PLUS | MINUS) multiplicativeExpression
	;

multiplicativeExpression
	:	unaryExpression
	|	multiplicativeExpression (MUL | DIV | MOD) unaryExpression
	;

unaryExpression
	:	incOrDecExpression
	|	(PLUS | MINUS | NOT) unaryExpression
	;

incOrDecExpression
    : primitive
    | (INC | DEC)? IDENTIFIER (INC | DEC)?
    | LPAREN expression RPAREN;

methodInvocation:	methodName LPAREN argumentList? RPAREN;

classInstanceCreationExpression: NEW argumentsType? string_dot_splitted typeArgumentsOrEmpty? LPAREN argumentList? RPAREN classBody?;