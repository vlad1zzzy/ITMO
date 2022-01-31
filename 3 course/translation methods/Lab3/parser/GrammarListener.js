// Generated from Grammar.g4 by ANTLR 4.8
// jshint ignore: start
var antlr4 = require('antlr4/index');

// This class defines a complete listener for a parse tree produced by GrammarParser.
function GrammarListener() {
	antlr4.tree.ParseTreeListener.call(this);
	return this;
}

GrammarListener.prototype = Object.create(antlr4.tree.ParseTreeListener.prototype);
GrammarListener.prototype.constructor = GrammarListener;

// Enter a parse tree produced by GrammarParser#compilationUnit.
GrammarListener.prototype.enterCompilationUnit = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#compilationUnit.
GrammarListener.prototype.exitCompilationUnit = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#primitive.
GrammarListener.prototype.enterPrimitive = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#primitive.
GrammarListener.prototype.exitPrimitive = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#primitiveType.
GrammarListener.prototype.enterPrimitiveType = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#primitiveType.
GrammarListener.prototype.exitPrimitiveType = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#referenceType.
GrammarListener.prototype.enterReferenceType = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#referenceType.
GrammarListener.prototype.exitReferenceType = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#classType.
GrammarListener.prototype.enterClassType = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#classType.
GrammarListener.prototype.exitClassType = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#variableType.
GrammarListener.prototype.enterVariableType = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#variableType.
GrammarListener.prototype.exitVariableType = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#arrayType.
GrammarListener.prototype.enterArrayType = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#arrayType.
GrammarListener.prototype.exitArrayType = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#brackets_square.
GrammarListener.prototype.enterBrackets_square = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#brackets_square.
GrammarListener.prototype.exitBrackets_square = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#typeParameter.
GrammarListener.prototype.enterTypeParameter = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#typeParameter.
GrammarListener.prototype.exitTypeParameter = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#typeBound.
GrammarListener.prototype.enterTypeBound = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#typeBound.
GrammarListener.prototype.exitTypeBound = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#argumentsType.
GrammarListener.prototype.enterArgumentsType = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#argumentsType.
GrammarListener.prototype.exitArgumentsType = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#typeArgumentList.
GrammarListener.prototype.enterTypeArgumentList = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#typeArgumentList.
GrammarListener.prototype.exitTypeArgumentList = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#typeArgument.
GrammarListener.prototype.enterTypeArgument = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#typeArgument.
GrammarListener.prototype.exitTypeArgument = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#wildcard.
GrammarListener.prototype.enterWildcard = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#wildcard.
GrammarListener.prototype.exitWildcard = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#wildcardBounds.
GrammarListener.prototype.enterWildcardBounds = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#wildcardBounds.
GrammarListener.prototype.exitWildcardBounds = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#string_dot_splitted.
GrammarListener.prototype.enterString_dot_splitted = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#string_dot_splitted.
GrammarListener.prototype.exitString_dot_splitted = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#argumentList.
GrammarListener.prototype.enterArgumentList = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#argumentList.
GrammarListener.prototype.exitArgumentList = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#methodName.
GrammarListener.prototype.enterMethodName = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#methodName.
GrammarListener.prototype.exitMethodName = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#packageDeclaration.
GrammarListener.prototype.enterPackageDeclaration = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#packageDeclaration.
GrammarListener.prototype.exitPackageDeclaration = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#importDeclaration.
GrammarListener.prototype.enterImportDeclaration = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#importDeclaration.
GrammarListener.prototype.exitImportDeclaration = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#classDeclaration.
GrammarListener.prototype.enterClassDeclaration = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#classDeclaration.
GrammarListener.prototype.exitClassDeclaration = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#classModifier.
GrammarListener.prototype.enterClassModifier = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#classModifier.
GrammarListener.prototype.exitClassModifier = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#typeParameters.
GrammarListener.prototype.enterTypeParameters = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#typeParameters.
GrammarListener.prototype.exitTypeParameters = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#typeArgumentsOrEmpty.
GrammarListener.prototype.enterTypeArgumentsOrEmpty = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#typeArgumentsOrEmpty.
GrammarListener.prototype.exitTypeArgumentsOrEmpty = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#typeParameterList.
GrammarListener.prototype.enterTypeParameterList = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#typeParameterList.
GrammarListener.prototype.exitTypeParameterList = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#superclass.
GrammarListener.prototype.enterSuperclass = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#superclass.
GrammarListener.prototype.exitSuperclass = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#superinterfaces.
GrammarListener.prototype.enterSuperinterfaces = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#superinterfaces.
GrammarListener.prototype.exitSuperinterfaces = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#classTypeList.
GrammarListener.prototype.enterClassTypeList = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#classTypeList.
GrammarListener.prototype.exitClassTypeList = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#classBody.
GrammarListener.prototype.enterClassBody = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#classBody.
GrammarListener.prototype.exitClassBody = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#classMemberDeclaration.
GrammarListener.prototype.enterClassMemberDeclaration = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#classMemberDeclaration.
GrammarListener.prototype.exitClassMemberDeclaration = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#fieldDeclaration.
GrammarListener.prototype.enterFieldDeclaration = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#fieldDeclaration.
GrammarListener.prototype.exitFieldDeclaration = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#fieldModifier.
GrammarListener.prototype.enterFieldModifier = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#fieldModifier.
GrammarListener.prototype.exitFieldModifier = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#variableDeclaratorList.
GrammarListener.prototype.enterVariableDeclaratorList = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#variableDeclaratorList.
GrammarListener.prototype.exitVariableDeclaratorList = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#variableDeclarator.
GrammarListener.prototype.enterVariableDeclarator = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#variableDeclarator.
GrammarListener.prototype.exitVariableDeclarator = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#variableDeclaratorId.
GrammarListener.prototype.enterVariableDeclaratorId = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#variableDeclaratorId.
GrammarListener.prototype.exitVariableDeclaratorId = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#variableInitializer.
GrammarListener.prototype.enterVariableInitializer = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#variableInitializer.
GrammarListener.prototype.exitVariableInitializer = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#methodDeclaration.
GrammarListener.prototype.enterMethodDeclaration = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#methodDeclaration.
GrammarListener.prototype.exitMethodDeclaration = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#methodModifier.
GrammarListener.prototype.enterMethodModifier = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#methodModifier.
GrammarListener.prototype.exitMethodModifier = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#methodHeader.
GrammarListener.prototype.enterMethodHeader = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#methodHeader.
GrammarListener.prototype.exitMethodHeader = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#result.
GrammarListener.prototype.enterResult = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#result.
GrammarListener.prototype.exitResult = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#methodDeclarator.
GrammarListener.prototype.enterMethodDeclarator = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#methodDeclarator.
GrammarListener.prototype.exitMethodDeclarator = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#formalParameterList.
GrammarListener.prototype.enterFormalParameterList = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#formalParameterList.
GrammarListener.prototype.exitFormalParameterList = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#formalParameter.
GrammarListener.prototype.enterFormalParameter = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#formalParameter.
GrammarListener.prototype.exitFormalParameter = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#variableModifier.
GrammarListener.prototype.enterVariableModifier = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#variableModifier.
GrammarListener.prototype.exitVariableModifier = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#throws_.
GrammarListener.prototype.enterThrows_ = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#throws_.
GrammarListener.prototype.exitThrows_ = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#exceptionTypeList.
GrammarListener.prototype.enterExceptionTypeList = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#exceptionTypeList.
GrammarListener.prototype.exitExceptionTypeList = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#exceptionType.
GrammarListener.prototype.enterExceptionType = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#exceptionType.
GrammarListener.prototype.exitExceptionType = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#methodBody.
GrammarListener.prototype.enterMethodBody = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#methodBody.
GrammarListener.prototype.exitMethodBody = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#constructorDeclaration.
GrammarListener.prototype.enterConstructorDeclaration = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#constructorDeclaration.
GrammarListener.prototype.exitConstructorDeclaration = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#constructorModifier.
GrammarListener.prototype.enterConstructorModifier = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#constructorModifier.
GrammarListener.prototype.exitConstructorModifier = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#constructorDeclarator.
GrammarListener.prototype.enterConstructorDeclarator = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#constructorDeclarator.
GrammarListener.prototype.exitConstructorDeclarator = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#constructorBody.
GrammarListener.prototype.enterConstructorBody = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#constructorBody.
GrammarListener.prototype.exitConstructorBody = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#explicitConstructorInvocation.
GrammarListener.prototype.enterExplicitConstructorInvocation = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#explicitConstructorInvocation.
GrammarListener.prototype.exitExplicitConstructorInvocation = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#arrayInitializer.
GrammarListener.prototype.enterArrayInitializer = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#arrayInitializer.
GrammarListener.prototype.exitArrayInitializer = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#variableInitializerList.
GrammarListener.prototype.enterVariableInitializerList = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#variableInitializerList.
GrammarListener.prototype.exitVariableInitializerList = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#block.
GrammarListener.prototype.enterBlock = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#block.
GrammarListener.prototype.exitBlock = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#localVariableDeclaration.
GrammarListener.prototype.enterLocalVariableDeclaration = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#localVariableDeclaration.
GrammarListener.prototype.exitLocalVariableDeclaration = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#statement.
GrammarListener.prototype.enterStatement = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#statement.
GrammarListener.prototype.exitStatement = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#statementWithoutTrailingSubstatement.
GrammarListener.prototype.enterStatementWithoutTrailingSubstatement = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#statementWithoutTrailingSubstatement.
GrammarListener.prototype.exitStatementWithoutTrailingSubstatement = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#expressionStatement.
GrammarListener.prototype.enterExpressionStatement = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#expressionStatement.
GrammarListener.prototype.exitExpressionStatement = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#statementExpression.
GrammarListener.prototype.enterStatementExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#statementExpression.
GrammarListener.prototype.exitStatementExpression = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#ifStatement.
GrammarListener.prototype.enterIfStatement = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#ifStatement.
GrammarListener.prototype.exitIfStatement = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#whileStatement.
GrammarListener.prototype.enterWhileStatement = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#whileStatement.
GrammarListener.prototype.exitWhileStatement = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#forStatement.
GrammarListener.prototype.enterForStatement = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#forStatement.
GrammarListener.prototype.exitForStatement = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#breakStatement.
GrammarListener.prototype.enterBreakStatement = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#breakStatement.
GrammarListener.prototype.exitBreakStatement = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#continueStatement.
GrammarListener.prototype.enterContinueStatement = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#continueStatement.
GrammarListener.prototype.exitContinueStatement = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#returnStatement.
GrammarListener.prototype.enterReturnStatement = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#returnStatement.
GrammarListener.prototype.exitReturnStatement = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#expression.
GrammarListener.prototype.enterExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#expression.
GrammarListener.prototype.exitExpression = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#assignment.
GrammarListener.prototype.enterAssignment = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#assignment.
GrammarListener.prototype.exitAssignment = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#assignmentOperator.
GrammarListener.prototype.enterAssignmentOperator = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#assignmentOperator.
GrammarListener.prototype.exitAssignmentOperator = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#conditionalExpression.
GrammarListener.prototype.enterConditionalExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#conditionalExpression.
GrammarListener.prototype.exitConditionalExpression = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#conditionalOrExpression.
GrammarListener.prototype.enterConditionalOrExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#conditionalOrExpression.
GrammarListener.prototype.exitConditionalOrExpression = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#conditionalAndExpression.
GrammarListener.prototype.enterConditionalAndExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#conditionalAndExpression.
GrammarListener.prototype.exitConditionalAndExpression = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#relationalExpression.
GrammarListener.prototype.enterRelationalExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#relationalExpression.
GrammarListener.prototype.exitRelationalExpression = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#additiveExpression.
GrammarListener.prototype.enterAdditiveExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#additiveExpression.
GrammarListener.prototype.exitAdditiveExpression = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#multiplicativeExpression.
GrammarListener.prototype.enterMultiplicativeExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#multiplicativeExpression.
GrammarListener.prototype.exitMultiplicativeExpression = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#unaryExpression.
GrammarListener.prototype.enterUnaryExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#unaryExpression.
GrammarListener.prototype.exitUnaryExpression = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#incOrDecExpression.
GrammarListener.prototype.enterIncOrDecExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#incOrDecExpression.
GrammarListener.prototype.exitIncOrDecExpression = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#methodInvocation.
GrammarListener.prototype.enterMethodInvocation = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#methodInvocation.
GrammarListener.prototype.exitMethodInvocation = function(ctx) {
};


// Enter a parse tree produced by GrammarParser#classInstanceCreationExpression.
GrammarListener.prototype.enterClassInstanceCreationExpression = function(ctx) {
};

// Exit a parse tree produced by GrammarParser#classInstanceCreationExpression.
GrammarListener.prototype.exitClassInstanceCreationExpression = function(ctx) {
};



exports.GrammarListener = GrammarListener;