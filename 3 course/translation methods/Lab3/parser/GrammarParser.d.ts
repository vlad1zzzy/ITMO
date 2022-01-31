import {CommonTokenStream, Parser, ParserRuleContext, Token} from 'antlr4';
import {TerminalNode} from 'antlr4/tree/Tree';


export declare class CompilationUnitContext extends ParserRuleContext {
    
    classDeclaration(): ClassDeclarationContext;
    
    packageDeclaration(): PackageDeclarationContext;
    
}

export declare class PrimitiveContext extends ParserRuleContext {
    
    INTEGER_VALUE(): TerminalNode;
    
    DOUBLE_VALUE(): TerminalNode;
    
    BOOLEAN_VALUE(): TerminalNode;
    
    STRING_VALUE(): TerminalNode;
    
    NULL(): TerminalNode;
    
}

export declare class PrimitiveTypeContext extends ParserRuleContext {
    
    BYTE(): TerminalNode;
    
    SHORT(): TerminalNode;
    
    INT(): TerminalNode;
    
    LONG(): TerminalNode;
    
    CHAR(): TerminalNode;
    
    FLOAT(): TerminalNode;
    
    DOUBLE(): TerminalNode;
    
    BOOLEAN(): TerminalNode;
    
}

export declare class ReferenceTypeContext extends ParserRuleContext {
    
    classType(): ClassTypeContext;
    
    variableType(): VariableTypeContext;
    
    arrayType(): ArrayTypeContext;
    
}

export declare class ClassTypeContext extends ParserRuleContext {
    
}

export declare class VariableTypeContext extends ParserRuleContext {
    
    TYPE(): TerminalNode;
    
    CLASS_IDENTIFIER(): TerminalNode;
    
}

export declare class ArrayTypeContext extends ParserRuleContext {
    
    primitiveType(): PrimitiveTypeContext;
    
    brackets_square(): Brackets_squareContext;
    
    classType(): ClassTypeContext;
    
    variableType(): VariableTypeContext;
    
}

export declare class Brackets_squareContext extends ParserRuleContext {
    
}

export declare class TypeParameterContext extends ParserRuleContext {
    
    IDENTIFIER(): TerminalNode;
    
    typeBound(): TypeBoundContext;
    
}

export declare class TypeBoundContext extends ParserRuleContext {
    
    EXTENDS(): TerminalNode;
    
    variableType(): VariableTypeContext;
    
    classType(): ClassTypeContext;
    
}

export declare class ArgumentsTypeContext extends ParserRuleContext {
    
    LT(): TerminalNode;
    
    typeArgumentList(): TypeArgumentListContext;
    
    GT(): TerminalNode;
    
}

export declare class TypeArgumentListContext extends ParserRuleContext {
    
}

export declare class TypeArgumentContext extends ParserRuleContext {
    
    referenceType(): ReferenceTypeContext;
    
    wildcard(): WildcardContext;
    
}

export declare class WildcardContext extends ParserRuleContext {
    
    IDENTIFIER(): TerminalNode;
    
    wildcardBounds(): WildcardBoundsContext;
    
}

export declare class WildcardBoundsContext extends ParserRuleContext {
    
    EXTENDS(): TerminalNode;
    
    referenceType(): ReferenceTypeContext;
    
    SUPER(): TerminalNode;
    
}

export declare class ArgumentListContext extends ParserRuleContext {
    
}

export declare class MethodNameContext extends ParserRuleContext {
    
    IDENTIFIER(): TerminalNode;
    
}

export declare class PackageDeclarationContext extends ParserRuleContext {
    
    PACKAGE(): TerminalNode;
    
    string_dot_splitted(): String_dot_splittedContext;
    
    SEMICOLON(): TerminalNode;
    
}

export declare class ImportDeclarationContext extends ParserRuleContext {
    
    IMPORT(): TerminalNode;
    
    string_dot_splitted(): String_dot_splittedContext;
    
    SEMICOLON(): TerminalNode;
    
    STATIC(): TerminalNode;
    
    DOT(): TerminalNode;
    
    MUL(): TerminalNode;
    
}

export declare class ClassDeclarationContext extends ParserRuleContext {
    
    CLASS(): TerminalNode;
    
    CLASS_IDENTIFIER(): TerminalNode;
    
    classBody(): ClassBodyContext;
    
    typeParameters(): TypeParametersContext;
    
    superclass(): SuperclassContext;
    
    superinterfaces(): SuperinterfacesContext;
    
}

export declare class ClassModifierContext extends ParserRuleContext {
    
    PUBLIC(): TerminalNode;
    
    PROTECTED(): TerminalNode;
    
    PRIVATE(): TerminalNode;
    
    ABSTRACT(): TerminalNode;
    
    STATIC(): TerminalNode;
    
    FINAL(): TerminalNode;
    
}

export declare class TypeParametersContext extends ParserRuleContext {
    
    LT(): TerminalNode;
    
    typeParameterList(): TypeParameterListContext;
    
    GT(): TerminalNode;
    
}

export declare class TypeArgumentsOrEmptyContext extends ParserRuleContext {
    
    typeParameters(): TypeParametersContext;
    
    LT(): TerminalNode;
    
    GT(): TerminalNode;
    
}

export declare class TypeParameterListContext extends ParserRuleContext {
    
}

export declare class SuperclassContext extends ParserRuleContext {
    
    EXTENDS(): TerminalNode;
    
    classType(): ClassTypeContext;
    
}

export declare class SuperinterfacesContext extends ParserRuleContext {
    
    IMPLEMENTS(): TerminalNode;
    
    classTypeList(): ClassTypeListContext;
    
}

export declare class ClassTypeListContext extends ParserRuleContext {
    
}

export declare class ClassBodyContext extends ParserRuleContext {
    
    LBRACE(): TerminalNode;
    
    RBRACE(): TerminalNode;
    
}

export declare class ClassMemberDeclarationContext extends ParserRuleContext {
    
    fieldDeclaration(): FieldDeclarationContext;
    
    methodDeclaration(): MethodDeclarationContext;
    
    constructorDeclaration(): ConstructorDeclarationContext;
    
    classDeclaration(): ClassDeclarationContext;
    
}

export declare class FieldDeclarationContext extends ParserRuleContext {
    
    variableDeclaratorList(): VariableDeclaratorListContext;
    
    SEMICOLON(): TerminalNode;
    
    primitiveType(): PrimitiveTypeContext;
    
    referenceType(): ReferenceTypeContext;
    
}

export declare class FieldModifierContext extends ParserRuleContext {
    
    PUBLIC(): TerminalNode;
    
    PROTECTED(): TerminalNode;
    
    PRIVATE(): TerminalNode;
    
    TRANSIENT(): TerminalNode;
    
    VOLATILE(): TerminalNode;
    
    STATIC(): TerminalNode;
    
    FINAL(): TerminalNode;
    
}

export declare class VariableDeclaratorListContext extends ParserRuleContext {
    
}

export declare class VariableDeclaratorContext extends ParserRuleContext {
    
    variableDeclaratorId(): VariableDeclaratorIdContext;
    
    ASSIGN(): TerminalNode;
    
    variableInitializer(): VariableInitializerContext;
    
}

export declare class VariableDeclaratorIdContext extends ParserRuleContext {
    
    IDENTIFIER(): TerminalNode;
    
    brackets_square(): Brackets_squareContext;
    
}

export declare class VariableInitializerContext extends ParserRuleContext {
    
    primitive(): PrimitiveContext;
    
    classInstanceCreationExpression(): ClassInstanceCreationExpressionContext;
    
    arrayInitializer(): ArrayInitializerContext;
    
}

export declare class MethodDeclarationContext extends ParserRuleContext {
    
    methodHeader(): MethodHeaderContext;
    
    methodBody(): MethodBodyContext;
    
}

export declare class MethodModifierContext extends ParserRuleContext {
    
    PUBLIC(): TerminalNode;
    
    PROTECTED(): TerminalNode;
    
    PRIVATE(): TerminalNode;
    
    ABSTRACT(): TerminalNode;
    
    STATIC(): TerminalNode;
    
    FINAL(): TerminalNode;
    
    SYNCHRONIZED(): TerminalNode;
    
    NATIVE(): TerminalNode;
    
}

export declare class MethodHeaderContext extends ParserRuleContext {
    
    result(): ResultContext;
    
    methodDeclarator(): MethodDeclaratorContext;
    
    throws_(): Throws_Context;
    
    typeParameters(): TypeParametersContext;
    
}

export declare class ResultContext extends ParserRuleContext {
    
    primitiveType(): PrimitiveTypeContext;
    
    classType(): ClassTypeContext;
    
    TYPE(): TerminalNode;
    
    VOID(): TerminalNode;
    
}

export declare class MethodDeclaratorContext extends ParserRuleContext {
    
    IDENTIFIER(): TerminalNode;
    
    LPAREN(): TerminalNode;
    
    RPAREN(): TerminalNode;
    
    formalParameterList(): FormalParameterListContext;
    
    brackets_square(): Brackets_squareContext;
    
}

export declare class FormalParameterListContext extends ParserRuleContext {
    
}

export declare class FormalParameterContext extends ParserRuleContext {
    
    variableDeclaratorId(): VariableDeclaratorIdContext;
    
    primitiveType(): PrimitiveTypeContext;
    
    referenceType(): ReferenceTypeContext;
    
}

export declare class VariableModifierContext extends ParserRuleContext {
    
    FINAL(): TerminalNode;
    
}

export declare class Throws_Context extends ParserRuleContext {
    
    THROWS(): TerminalNode;
    
    exceptionTypeList(): ExceptionTypeListContext;
    
}

export declare class ExceptionTypeListContext extends ParserRuleContext {
    
}

export declare class ExceptionTypeContext extends ParserRuleContext {
    
    classType(): ClassTypeContext;
    
    variableType(): VariableTypeContext;
    
}

export declare class MethodBodyContext extends ParserRuleContext {
    
    block(): BlockContext;
    
    SEMICOLON(): TerminalNode;
    
}

export declare class ConstructorDeclarationContext extends ParserRuleContext {
    
    constructorDeclarator(): ConstructorDeclaratorContext;
    
    constructorBody(): ConstructorBodyContext;
    
    throws_(): Throws_Context;
    
}

export declare class ConstructorModifierContext extends ParserRuleContext {
    
    PUBLIC(): TerminalNode;
    
    PROTECTED(): TerminalNode;
    
    PRIVATE(): TerminalNode;
    
}

export declare class ConstructorDeclaratorContext extends ParserRuleContext {
    
    CLASS_IDENTIFIER(): TerminalNode;
    
    LPAREN(): TerminalNode;
    
    RPAREN(): TerminalNode;
    
    typeParameters(): TypeParametersContext;
    
    formalParameterList(): FormalParameterListContext;
    
}

export declare class ConstructorBodyContext extends ParserRuleContext {
    
    LBRACE(): TerminalNode;
    
    RBRACE(): TerminalNode;
    
    explicitConstructorInvocation(): ExplicitConstructorInvocationContext;
    
}

export declare class ExplicitConstructorInvocationContext extends ParserRuleContext {
    
    LPAREN(): TerminalNode;
    
    RPAREN(): TerminalNode;
    
    SEMICOLON(): TerminalNode;
    
    THIS(): TerminalNode;
    
    SUPER(): TerminalNode;
    
    argumentsType(): ArgumentsTypeContext;
    
    argumentList(): ArgumentListContext;
    
}

export declare class ArrayInitializerContext extends ParserRuleContext {
    
    LBRACE(): TerminalNode;
    
    RBRACE(): TerminalNode;
    
    variableInitializerList(): VariableInitializerListContext;
    
    COMMA(): TerminalNode;
    
}

export declare class VariableInitializerListContext extends ParserRuleContext {
    
}

export declare class BlockContext extends ParserRuleContext {
    
    LBRACE(): TerminalNode;
    
    RBRACE(): TerminalNode;
    
}

export declare class LocalVariableDeclarationContext extends ParserRuleContext {
    
    variableDeclaratorList(): VariableDeclaratorListContext;
    
    SEMICOLON(): TerminalNode;
    
    primitiveType(): PrimitiveTypeContext;
    
    referenceType(): ReferenceTypeContext;
    
}

export declare class StatementContext extends ParserRuleContext {
    
    statementWithoutTrailingSubstatement(): StatementWithoutTrailingSubstatementContext;
    
    ifStatement(): IfStatementContext;
    
    whileStatement(): WhileStatementContext;
    
    forStatement(): ForStatementContext;
    
    localVariableDeclaration(): LocalVariableDeclarationContext;
    
    assignment(): AssignmentContext;
    
}

export declare class StatementWithoutTrailingSubstatementContext extends ParserRuleContext {
    
    expressionStatement(): ExpressionStatementContext;
    
    breakStatement(): BreakStatementContext;
    
    continueStatement(): ContinueStatementContext;
    
    returnStatement(): ReturnStatementContext;
    
}

export declare class ExpressionStatementContext extends ParserRuleContext {
    
    statementExpression(): StatementExpressionContext;
    
    SEMICOLON(): TerminalNode;
    
}

export declare class StatementExpressionContext extends ParserRuleContext {
    
    assignment(): AssignmentContext;
    
    methodInvocation(): MethodInvocationContext;
    
    classInstanceCreationExpression(): ClassInstanceCreationExpressionContext;
    
}

export declare class IfStatementContext extends ParserRuleContext {
    
    IF(): TerminalNode;
    
    LPAREN(): TerminalNode;
    
    expression(): ExpressionContext;
    
    RPAREN(): TerminalNode;
    
    ELSE(): TerminalNode;
    
}

export declare class WhileStatementContext extends ParserRuleContext {
    
    WHILE(): TerminalNode;
    
    LPAREN(): TerminalNode;
    
    expression(): ExpressionContext;
    
    RPAREN(): TerminalNode;
    
    block(): BlockContext;
    
    statement(): StatementContext;
    
    SEMICOLON(): TerminalNode;
    
}

export declare class ForStatementContext extends ParserRuleContext {
    
    FOR(): TerminalNode;
    
    LPAREN(): TerminalNode;
    
    RPAREN(): TerminalNode;
    
    block(): BlockContext;
    
    localVariableDeclaration(): LocalVariableDeclarationContext;
    
    expression(): ExpressionContext;
    
    incOrDecExpression(): IncOrDecExpressionContext;
    
}

export declare class BreakStatementContext extends ParserRuleContext {
    
    BREAK(): TerminalNode;
    
    SEMICOLON(): TerminalNode;
    
}

export declare class ContinueStatementContext extends ParserRuleContext {
    
    CONTINUE(): TerminalNode;
    
    SEMICOLON(): TerminalNode;
    
}

export declare class ReturnStatementContext extends ParserRuleContext {
    
    RETURN(): TerminalNode;
    
    SEMICOLON(): TerminalNode;
    
    expression(): ExpressionContext;
    
}

export declare class ExpressionContext extends ParserRuleContext {
    
    primitive(): PrimitiveContext;
    
    conditionalExpression(): ConditionalExpressionContext;
    
    classInstanceCreationExpression(): ClassInstanceCreationExpressionContext;
    
}

export declare class AssignmentContext extends ParserRuleContext {
    
    IDENTIFIER(): TerminalNode;
    
    assignmentOperator(): AssignmentOperatorContext;
    
    expression(): ExpressionContext;
    
    TYPE(): TerminalNode;
    
    primitiveType(): PrimitiveTypeContext;
    
    referenceType(): ReferenceTypeContext;
    
    incOrDecExpression(): IncOrDecExpressionContext;
    
}

export declare class AssignmentOperatorContext extends ParserRuleContext {
    
    ASSIGN(): TerminalNode;
    
    MUL(): TerminalNode;
    
    DIV(): TerminalNode;
    
    MOD(): TerminalNode;
    
    PLUS(): TerminalNode;
    
    MINUS(): TerminalNode;
    
}

export declare class UnaryExpressionContext extends ParserRuleContext {
    
    incOrDecExpression(): IncOrDecExpressionContext;
    
    unaryExpression(): UnaryExpressionContext;
    
    PLUS(): TerminalNode;
    
    MINUS(): TerminalNode;
    
    NOT(): TerminalNode;
    
}

export declare class IncOrDecExpressionContext extends ParserRuleContext {
    
    primitive(): PrimitiveContext;
    
    IDENTIFIER(): TerminalNode;
    
    LPAREN(): TerminalNode;
    
    expression(): ExpressionContext;
    
    RPAREN(): TerminalNode;
    
}

export declare class MethodInvocationContext extends ParserRuleContext {
    
    methodName(): MethodNameContext;
    
    LPAREN(): TerminalNode;
    
    RPAREN(): TerminalNode;
    
    argumentList(): ArgumentListContext;
    
}

export declare class ClassInstanceCreationExpressionContext extends ParserRuleContext {
    
    NEW(): TerminalNode;
    
    string_dot_splitted(): String_dot_splittedContext;
    
    LPAREN(): TerminalNode;
    
    RPAREN(): TerminalNode;
    
    argumentsType(): ArgumentsTypeContext;
    
    typeArgumentsOrEmpty(): TypeArgumentsOrEmptyContext;
    
    argumentList(): ArgumentListContext;
    
    classBody(): ClassBodyContext;
    
}

export declare class String_dot_splittedContext extends ParserRuleContext {
    
    IDENTIFIER(): TerminalNode;
    
    CLASS_IDENTIFIER(): TerminalNode;
    
    string_dot_splitted(): String_dot_splittedContext;
    
    DOT(): TerminalNode;
    
}

export declare class ConditionalExpressionContext extends ParserRuleContext {
    
    conditionalOrExpression(): ConditionalOrExpressionContext;
    
    conditionalExpression(): ConditionalExpressionContext;
    
    Q_MARK(): TerminalNode;
    
    expression(): ExpressionContext;
    
    COMMA(): TerminalNode;
    
}

export declare class ConditionalOrExpressionContext extends ParserRuleContext {
    
    conditionalAndExpression(): ConditionalAndExpressionContext;
    
    conditionalOrExpression(): ConditionalOrExpressionContext;
    
    OR(): TerminalNode;
    
}

export declare class ConditionalAndExpressionContext extends ParserRuleContext {
    
    relationalExpression(): RelationalExpressionContext;
    
    conditionalAndExpression(): ConditionalAndExpressionContext;
    
    AND(): TerminalNode;
    
}

export declare class RelationalExpressionContext extends ParserRuleContext {
    
    additiveExpression(): AdditiveExpressionContext;
    
    relationalExpression(): RelationalExpressionContext;
    
    LT(): TerminalNode;
    
    GT(): TerminalNode;
    
    LE(): TerminalNode;
    
    GE(): TerminalNode;
    
    EQ(): TerminalNode;
    
    NOT_EQ(): TerminalNode;
    
}

export declare class AdditiveExpressionContext extends ParserRuleContext {
    
    multiplicativeExpression(): MultiplicativeExpressionContext;
    
    additiveExpression(): AdditiveExpressionContext;
    
    PLUS(): TerminalNode;
    
    MINUS(): TerminalNode;
    
}

export declare class MultiplicativeExpressionContext extends ParserRuleContext {
    
    unaryExpression(): UnaryExpressionContext;
    
    multiplicativeExpression(): MultiplicativeExpressionContext;
    
    MUL(): TerminalNode;
    
    DIV(): TerminalNode;
    
    MOD(): TerminalNode;
    
}


export declare class GrammarParser extends Parser {
    readonly ruleNames: string[];
    readonly literalNames: string[];
    readonly symbolicNames: string[];

    constructor(input: CommonTokenStream);
    
    compilationUnit(): CompilationUnitContext;

    primitive(): PrimitiveContext;

    primitiveType(): PrimitiveTypeContext;

    referenceType(): ReferenceTypeContext;

    classType(): ClassTypeContext;

    variableType(): VariableTypeContext;

    arrayType(): ArrayTypeContext;

    brackets_square(): Brackets_squareContext;

    typeParameter(): TypeParameterContext;

    typeBound(): TypeBoundContext;

    argumentsType(): ArgumentsTypeContext;

    typeArgumentList(): TypeArgumentListContext;

    typeArgument(): TypeArgumentContext;

    wildcard(): WildcardContext;

    wildcardBounds(): WildcardBoundsContext;

    argumentList(): ArgumentListContext;

    methodName(): MethodNameContext;

    packageDeclaration(): PackageDeclarationContext;

    importDeclaration(): ImportDeclarationContext;

    classDeclaration(): ClassDeclarationContext;

    classModifier(): ClassModifierContext;

    typeParameters(): TypeParametersContext;

    typeArgumentsOrEmpty(): TypeArgumentsOrEmptyContext;

    typeParameterList(): TypeParameterListContext;

    superclass(): SuperclassContext;

    superinterfaces(): SuperinterfacesContext;

    classTypeList(): ClassTypeListContext;

    classBody(): ClassBodyContext;

    classMemberDeclaration(): ClassMemberDeclarationContext;

    fieldDeclaration(): FieldDeclarationContext;

    fieldModifier(): FieldModifierContext;

    variableDeclaratorList(): VariableDeclaratorListContext;

    variableDeclarator(): VariableDeclaratorContext;

    variableDeclaratorId(): VariableDeclaratorIdContext;

    variableInitializer(): VariableInitializerContext;

    methodDeclaration(): MethodDeclarationContext;

    methodModifier(): MethodModifierContext;

    methodHeader(): MethodHeaderContext;

    result(): ResultContext;

    methodDeclarator(): MethodDeclaratorContext;

    formalParameterList(): FormalParameterListContext;

    formalParameter(): FormalParameterContext;

    variableModifier(): VariableModifierContext;

    throws_(): Throws_Context;

    exceptionTypeList(): ExceptionTypeListContext;

    exceptionType(): ExceptionTypeContext;

    methodBody(): MethodBodyContext;

    constructorDeclaration(): ConstructorDeclarationContext;

    constructorModifier(): ConstructorModifierContext;

    constructorDeclarator(): ConstructorDeclaratorContext;

    constructorBody(): ConstructorBodyContext;

    explicitConstructorInvocation(): ExplicitConstructorInvocationContext;

    arrayInitializer(): ArrayInitializerContext;

    variableInitializerList(): VariableInitializerListContext;

    block(): BlockContext;

    localVariableDeclaration(): LocalVariableDeclarationContext;

    statement(): StatementContext;

    statementWithoutTrailingSubstatement(): StatementWithoutTrailingSubstatementContext;

    expressionStatement(): ExpressionStatementContext;

    statementExpression(): StatementExpressionContext;

    ifStatement(): IfStatementContext;

    whileStatement(): WhileStatementContext;

    forStatement(): ForStatementContext;

    breakStatement(): BreakStatementContext;

    continueStatement(): ContinueStatementContext;

    returnStatement(): ReturnStatementContext;

    expression(): ExpressionContext;

    assignment(): AssignmentContext;

    assignmentOperator(): AssignmentOperatorContext;

    unaryExpression(): UnaryExpressionContext;

    incOrDecExpression(): IncOrDecExpressionContext;

    methodInvocation(): MethodInvocationContext;

    classInstanceCreationExpression(): ClassInstanceCreationExpressionContext;

}
