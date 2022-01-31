import {CommonTokenStream, ParserRuleContext, Token} from 'antlr4';
import {ErrorNode, ParseTreeListener, TerminalNode} from 'antlr4/tree/Tree';

import {CompilationUnitContext} from './GrammarParser';

import {PrimitiveContext} from './GrammarParser';

import {PrimitiveTypeContext} from './GrammarParser';

import {ReferenceTypeContext} from './GrammarParser';

import {ClassTypeContext} from './GrammarParser';

import {VariableTypeContext} from './GrammarParser';

import {ArrayTypeContext} from './GrammarParser';

import {Brackets_squareContext} from './GrammarParser';

import {TypeParameterContext} from './GrammarParser';

import {TypeBoundContext} from './GrammarParser';

import {ArgumentsTypeContext} from './GrammarParser';

import {TypeArgumentListContext} from './GrammarParser';

import {TypeArgumentContext} from './GrammarParser';

import {WildcardContext} from './GrammarParser';

import {WildcardBoundsContext} from './GrammarParser';

import {String_dot_splittedContext} from './GrammarParser';

import {ArgumentListContext} from './GrammarParser';

import {MethodNameContext} from './GrammarParser';

import {PackageDeclarationContext} from './GrammarParser';

import {ImportDeclarationContext} from './GrammarParser';

import {ClassDeclarationContext} from './GrammarParser';

import {ClassModifierContext} from './GrammarParser';

import {TypeParametersContext} from './GrammarParser';

import {TypeArgumentsOrEmptyContext} from './GrammarParser';

import {TypeParameterListContext} from './GrammarParser';

import {SuperclassContext} from './GrammarParser';

import {SuperinterfacesContext} from './GrammarParser';

import {ClassTypeListContext} from './GrammarParser';

import {ClassBodyContext} from './GrammarParser';

import {ClassMemberDeclarationContext} from './GrammarParser';

import {FieldDeclarationContext} from './GrammarParser';

import {FieldModifierContext} from './GrammarParser';

import {VariableDeclaratorListContext} from './GrammarParser';

import {VariableDeclaratorContext} from './GrammarParser';

import {VariableDeclaratorIdContext} from './GrammarParser';

import {VariableInitializerContext} from './GrammarParser';

import {MethodDeclarationContext} from './GrammarParser';

import {MethodModifierContext} from './GrammarParser';

import {MethodHeaderContext} from './GrammarParser';

import {ResultContext} from './GrammarParser';

import {MethodDeclaratorContext} from './GrammarParser';

import {FormalParameterListContext} from './GrammarParser';

import {FormalParameterContext} from './GrammarParser';

import {VariableModifierContext} from './GrammarParser';

import {Throws_Context} from './GrammarParser';

import {ExceptionTypeListContext} from './GrammarParser';

import {ExceptionTypeContext} from './GrammarParser';

import {MethodBodyContext} from './GrammarParser';

import {ConstructorDeclarationContext} from './GrammarParser';

import {ConstructorModifierContext} from './GrammarParser';

import {ConstructorDeclaratorContext} from './GrammarParser';

import {ConstructorBodyContext} from './GrammarParser';

import {ExplicitConstructorInvocationContext} from './GrammarParser';

import {ArrayInitializerContext} from './GrammarParser';

import {VariableInitializerListContext} from './GrammarParser';

import {BlockContext} from './GrammarParser';

import {LocalVariableDeclarationContext} from './GrammarParser';

import {StatementContext} from './GrammarParser';

import {StatementWithoutTrailingSubstatementContext} from './GrammarParser';

import {ExpressionStatementContext} from './GrammarParser';

import {StatementExpressionContext} from './GrammarParser';

import {IfStatementContext} from './GrammarParser';

import {WhileStatementContext} from './GrammarParser';

import {ForStatementContext} from './GrammarParser';

import {BreakStatementContext} from './GrammarParser';

import {ContinueStatementContext} from './GrammarParser';

import {ReturnStatementContext} from './GrammarParser';

import {ExpressionContext} from './GrammarParser';

import {AssignmentContext} from './GrammarParser';

import {AssignmentOperatorContext} from './GrammarParser';

import {ConditionalExpressionContext} from './GrammarParser';

import {ConditionalOrExpressionContext} from './GrammarParser';

import {ConditionalAndExpressionContext} from './GrammarParser';

import {RelationalExpressionContext} from './GrammarParser';

import {AdditiveExpressionContext} from './GrammarParser';

import {MultiplicativeExpressionContext} from './GrammarParser';

import {UnaryExpressionContext} from './GrammarParser';

import {IncOrDecExpressionContext} from './GrammarParser';

import {MethodInvocationContext} from './GrammarParser';

import {ClassInstanceCreationExpressionContext} from './GrammarParser';


export declare class GrammarListener implements ParseTreeListener {
    constructor();
    
    enterCompilationUnit(ctx: CompilationUnitContext): void;
    
    exitCompilationUnit(ctx: CompilationUnitContext): void;
    
    enterPrimitive(ctx: PrimitiveContext): void;
    
    exitPrimitive(ctx: PrimitiveContext): void;
    
    enterPrimitiveType(ctx: PrimitiveTypeContext): void;
    
    exitPrimitiveType(ctx: PrimitiveTypeContext): void;
    
    enterReferenceType(ctx: ReferenceTypeContext): void;
    
    exitReferenceType(ctx: ReferenceTypeContext): void;
    
    enterClassType(ctx: ClassTypeContext): void;
    
    exitClassType(ctx: ClassTypeContext): void;
    
    enterVariableType(ctx: VariableTypeContext): void;
    
    exitVariableType(ctx: VariableTypeContext): void;
    
    enterArrayType(ctx: ArrayTypeContext): void;
    
    exitArrayType(ctx: ArrayTypeContext): void;
    
    enterBrackets_square(ctx: Brackets_squareContext): void;
    
    exitBrackets_square(ctx: Brackets_squareContext): void;
    
    enterTypeParameter(ctx: TypeParameterContext): void;
    
    exitTypeParameter(ctx: TypeParameterContext): void;
    
    enterTypeBound(ctx: TypeBoundContext): void;
    
    exitTypeBound(ctx: TypeBoundContext): void;
    
    enterArgumentsType(ctx: ArgumentsTypeContext): void;
    
    exitArgumentsType(ctx: ArgumentsTypeContext): void;
    
    enterTypeArgumentList(ctx: TypeArgumentListContext): void;
    
    exitTypeArgumentList(ctx: TypeArgumentListContext): void;
    
    enterTypeArgument(ctx: TypeArgumentContext): void;
    
    exitTypeArgument(ctx: TypeArgumentContext): void;
    
    enterWildcard(ctx: WildcardContext): void;
    
    exitWildcard(ctx: WildcardContext): void;
    
    enterWildcardBounds(ctx: WildcardBoundsContext): void;
    
    exitWildcardBounds(ctx: WildcardBoundsContext): void;
    
    enterString_dot_splitted(ctx: String_dot_splittedContext): void;
    
    exitString_dot_splitted(ctx: String_dot_splittedContext): void;
    
    enterArgumentList(ctx: ArgumentListContext): void;
    
    exitArgumentList(ctx: ArgumentListContext): void;
    
    enterMethodName(ctx: MethodNameContext): void;
    
    exitMethodName(ctx: MethodNameContext): void;
    
    enterPackageDeclaration(ctx: PackageDeclarationContext): void;
    
    exitPackageDeclaration(ctx: PackageDeclarationContext): void;
    
    enterImportDeclaration(ctx: ImportDeclarationContext): void;
    
    exitImportDeclaration(ctx: ImportDeclarationContext): void;
    
    enterClassDeclaration(ctx: ClassDeclarationContext): void;
    
    exitClassDeclaration(ctx: ClassDeclarationContext): void;
    
    enterClassModifier(ctx: ClassModifierContext): void;
    
    exitClassModifier(ctx: ClassModifierContext): void;
    
    enterTypeParameters(ctx: TypeParametersContext): void;
    
    exitTypeParameters(ctx: TypeParametersContext): void;
    
    enterTypeArgumentsOrEmpty(ctx: TypeArgumentsOrEmptyContext): void;
    
    exitTypeArgumentsOrEmpty(ctx: TypeArgumentsOrEmptyContext): void;
    
    enterTypeParameterList(ctx: TypeParameterListContext): void;
    
    exitTypeParameterList(ctx: TypeParameterListContext): void;
    
    enterSuperclass(ctx: SuperclassContext): void;
    
    exitSuperclass(ctx: SuperclassContext): void;
    
    enterSuperinterfaces(ctx: SuperinterfacesContext): void;
    
    exitSuperinterfaces(ctx: SuperinterfacesContext): void;
    
    enterClassTypeList(ctx: ClassTypeListContext): void;
    
    exitClassTypeList(ctx: ClassTypeListContext): void;
    
    enterClassBody(ctx: ClassBodyContext): void;
    
    exitClassBody(ctx: ClassBodyContext): void;
    
    enterClassMemberDeclaration(ctx: ClassMemberDeclarationContext): void;
    
    exitClassMemberDeclaration(ctx: ClassMemberDeclarationContext): void;
    
    enterFieldDeclaration(ctx: FieldDeclarationContext): void;
    
    exitFieldDeclaration(ctx: FieldDeclarationContext): void;
    
    enterFieldModifier(ctx: FieldModifierContext): void;
    
    exitFieldModifier(ctx: FieldModifierContext): void;
    
    enterVariableDeclaratorList(ctx: VariableDeclaratorListContext): void;
    
    exitVariableDeclaratorList(ctx: VariableDeclaratorListContext): void;
    
    enterVariableDeclarator(ctx: VariableDeclaratorContext): void;
    
    exitVariableDeclarator(ctx: VariableDeclaratorContext): void;
    
    enterVariableDeclaratorId(ctx: VariableDeclaratorIdContext): void;
    
    exitVariableDeclaratorId(ctx: VariableDeclaratorIdContext): void;
    
    enterVariableInitializer(ctx: VariableInitializerContext): void;
    
    exitVariableInitializer(ctx: VariableInitializerContext): void;
    
    enterMethodDeclaration(ctx: MethodDeclarationContext): void;
    
    exitMethodDeclaration(ctx: MethodDeclarationContext): void;
    
    enterMethodModifier(ctx: MethodModifierContext): void;
    
    exitMethodModifier(ctx: MethodModifierContext): void;
    
    enterMethodHeader(ctx: MethodHeaderContext): void;
    
    exitMethodHeader(ctx: MethodHeaderContext): void;
    
    enterResult(ctx: ResultContext): void;
    
    exitResult(ctx: ResultContext): void;
    
    enterMethodDeclarator(ctx: MethodDeclaratorContext): void;
    
    exitMethodDeclarator(ctx: MethodDeclaratorContext): void;
    
    enterFormalParameterList(ctx: FormalParameterListContext): void;
    
    exitFormalParameterList(ctx: FormalParameterListContext): void;
    
    enterFormalParameter(ctx: FormalParameterContext): void;
    
    exitFormalParameter(ctx: FormalParameterContext): void;
    
    enterVariableModifier(ctx: VariableModifierContext): void;
    
    exitVariableModifier(ctx: VariableModifierContext): void;
    
    enterThrows_(ctx: Throws_Context): void;
    
    exitThrows_(ctx: Throws_Context): void;
    
    enterExceptionTypeList(ctx: ExceptionTypeListContext): void;
    
    exitExceptionTypeList(ctx: ExceptionTypeListContext): void;
    
    enterExceptionType(ctx: ExceptionTypeContext): void;
    
    exitExceptionType(ctx: ExceptionTypeContext): void;
    
    enterMethodBody(ctx: MethodBodyContext): void;
    
    exitMethodBody(ctx: MethodBodyContext): void;
    
    enterConstructorDeclaration(ctx: ConstructorDeclarationContext): void;
    
    exitConstructorDeclaration(ctx: ConstructorDeclarationContext): void;
    
    enterConstructorModifier(ctx: ConstructorModifierContext): void;
    
    exitConstructorModifier(ctx: ConstructorModifierContext): void;
    
    enterConstructorDeclarator(ctx: ConstructorDeclaratorContext): void;
    
    exitConstructorDeclarator(ctx: ConstructorDeclaratorContext): void;
    
    enterConstructorBody(ctx: ConstructorBodyContext): void;
    
    exitConstructorBody(ctx: ConstructorBodyContext): void;
    
    enterExplicitConstructorInvocation(ctx: ExplicitConstructorInvocationContext): void;
    
    exitExplicitConstructorInvocation(ctx: ExplicitConstructorInvocationContext): void;
    
    enterArrayInitializer(ctx: ArrayInitializerContext): void;
    
    exitArrayInitializer(ctx: ArrayInitializerContext): void;
    
    enterVariableInitializerList(ctx: VariableInitializerListContext): void;
    
    exitVariableInitializerList(ctx: VariableInitializerListContext): void;
    
    enterBlock(ctx: BlockContext): void;
    
    exitBlock(ctx: BlockContext): void;
    
    enterLocalVariableDeclaration(ctx: LocalVariableDeclarationContext): void;
    
    exitLocalVariableDeclaration(ctx: LocalVariableDeclarationContext): void;
    
    enterStatement(ctx: StatementContext): void;
    
    exitStatement(ctx: StatementContext): void;
    
    enterStatementWithoutTrailingSubstatement(ctx: StatementWithoutTrailingSubstatementContext): void;
    
    exitStatementWithoutTrailingSubstatement(ctx: StatementWithoutTrailingSubstatementContext): void;
    
    enterExpressionStatement(ctx: ExpressionStatementContext): void;
    
    exitExpressionStatement(ctx: ExpressionStatementContext): void;
    
    enterStatementExpression(ctx: StatementExpressionContext): void;
    
    exitStatementExpression(ctx: StatementExpressionContext): void;
    
    enterIfStatement(ctx: IfStatementContext): void;
    
    exitIfStatement(ctx: IfStatementContext): void;
    
    enterWhileStatement(ctx: WhileStatementContext): void;
    
    exitWhileStatement(ctx: WhileStatementContext): void;
    
    enterForStatement(ctx: ForStatementContext): void;
    
    exitForStatement(ctx: ForStatementContext): void;
    
    enterBreakStatement(ctx: BreakStatementContext): void;
    
    exitBreakStatement(ctx: BreakStatementContext): void;
    
    enterContinueStatement(ctx: ContinueStatementContext): void;
    
    exitContinueStatement(ctx: ContinueStatementContext): void;
    
    enterReturnStatement(ctx: ReturnStatementContext): void;
    
    exitReturnStatement(ctx: ReturnStatementContext): void;
    
    enterExpression(ctx: ExpressionContext): void;
    
    exitExpression(ctx: ExpressionContext): void;
    
    enterAssignment(ctx: AssignmentContext): void;
    
    exitAssignment(ctx: AssignmentContext): void;
    
    enterAssignmentOperator(ctx: AssignmentOperatorContext): void;
    
    exitAssignmentOperator(ctx: AssignmentOperatorContext): void;
    
    enterConditionalExpression(ctx: ConditionalExpressionContext): void;
    
    exitConditionalExpression(ctx: ConditionalExpressionContext): void;
    
    enterConditionalOrExpression(ctx: ConditionalOrExpressionContext): void;
    
    exitConditionalOrExpression(ctx: ConditionalOrExpressionContext): void;
    
    enterConditionalAndExpression(ctx: ConditionalAndExpressionContext): void;
    
    exitConditionalAndExpression(ctx: ConditionalAndExpressionContext): void;
    
    enterRelationalExpression(ctx: RelationalExpressionContext): void;
    
    exitRelationalExpression(ctx: RelationalExpressionContext): void;
    
    enterAdditiveExpression(ctx: AdditiveExpressionContext): void;
    
    exitAdditiveExpression(ctx: AdditiveExpressionContext): void;
    
    enterMultiplicativeExpression(ctx: MultiplicativeExpressionContext): void;
    
    exitMultiplicativeExpression(ctx: MultiplicativeExpressionContext): void;
    
    enterUnaryExpression(ctx: UnaryExpressionContext): void;
    
    exitUnaryExpression(ctx: UnaryExpressionContext): void;
    
    enterIncOrDecExpression(ctx: IncOrDecExpressionContext): void;
    
    exitIncOrDecExpression(ctx: IncOrDecExpressionContext): void;
    
    enterMethodInvocation(ctx: MethodInvocationContext): void;
    
    exitMethodInvocation(ctx: MethodInvocationContext): void;
    
    enterClassInstanceCreationExpression(ctx: ClassInstanceCreationExpressionContext): void;
    
    exitClassInstanceCreationExpression(ctx: ClassInstanceCreationExpressionContext): void;
    
    visitTerminal(node: TerminalNode): void;

    visitErrorNode(node: ErrorNode): void;

    enterEveryRule(node: ParserRuleContext): void;

    exitEveryRule(node: ParserRuleContext): void;
}
