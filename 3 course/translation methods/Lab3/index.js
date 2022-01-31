const antlr4 = require('antlr4')
const InputStream = antlr4.InputStream;
const CommonTokenStream = antlr4.CommonTokenStream;

const GrammarParser = require('./parser/GrammarParser').GrammarParser;
const GrammarLexer = require('./parser/GrammarLexer').GrammarLexer;
const GrammarListener = require('./parser/GrammarListener').GrammarListener;
const GrammarVisitor = require('./parser/GrammarVisitor').GrammarVisitor;

const fs = require("fs");
const {ParseTreeWalker, ParseTreeVisitor} = require("antlr4/tree/Tree");
const text = fs.readFileSync(__dirname + "\\Test.java", "utf-8");

const inputStream = new InputStream(text);
const lexer = new GrammarLexer(inputStream);
const tokenStream = new CommonTokenStream(lexer);

const parser = new GrammarParser(tokenStream);

const tree = parser.compilationUnit();

let pretty = '';

class Visitor extends GrammarVisitor {
    tabs = 0;

    getTabs() {
        return '    '.repeat(this.tabs);
    }

    visitCompilationUnit(ctx) {
        pretty += this.visitPackageDeclaration(ctx.packageDeclaration());

        let imp, i = 0;
        while (imp = this.visitImportDeclaration(ctx.importDeclaration(i++))) {
            pretty += imp;
        }

        pretty += '\n';
        pretty += this.visitClassDeclaration(ctx.classDeclaration());
    }

    visitPackageDeclaration(ctx) {
        if (!ctx) return '';

        return (
            ctx.getChild(0).getText()
            + ' '
            + ctx.getChild(1).getText()
            + ctx.getChild(2).getText()
            + '\n\n'
        );
    }

    visitImportDeclaration(ctx) {
        if (!ctx) return '';

        return (
            ctx.getChild(0).getText()
            + ' '
            + ctx.getChild(1).getText()
            + ctx.getChild(2).getText()
            + '\n'
        );
    }

    visitClassDeclaration(ctx) {
        if (!ctx) return '';

        let imp, i = 0, mods = [];
        while (imp = this.visitClassModifier(ctx.classModifier(i++))) {
            mods.push(imp);
        }

        return ('\n' + this.getTabs() + [
            ...mods,
            ctx.getChild(--i).getText(),
            ctx.getChild(++i).getText(),
            this.visitSuperinterfaces(ctx.superinterfaces()),
            this.visitClassBody(ctx.classBody()),
        ].filter(i => i).join(' '));
    }

    visitClassModifier(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitSuperinterfaces(ctx) {
        if (!ctx) return '';
        return (
            ctx.getChild(0).getText()
            + ' '
            + ctx.getChild(1).getText()
        );
    }

    visitClassBody(ctx) {
        if (!ctx) return '';

        let imp, i = 0, mods = [ctx.getChild(0).getText()];
        this.tabs++;
        while (imp = this.visitClassMemberDeclaration(ctx.classMemberDeclaration(i++))) {
            mods.push(this.getTabs() + imp);
        }
        this.tabs--;
        mods.push(this.getTabs() + ctx.getChild(i++).getText());

        return mods.filter(i => i).join('\n');
    }

    visitClassMemberDeclaration(ctx) {
        if (!ctx) return '';

        return (
            this.visitFieldDeclaration(ctx.fieldDeclaration())
            || this.visitMethodDeclaration(ctx.methodDeclaration())
            || this.visitConstructorDeclaration(ctx.constructorDeclaration())
            || this.visitClassDeclaration(ctx.classDeclaration())
        );
    }

    visitFieldDeclaration(ctx) {
        if (!ctx) return '';

        let imp, i = 0, mods = [];
        while (imp = this.visitFieldModifier(ctx.fieldModifier(i++))) {
            mods.push(imp);
        }

        mods.push(ctx.getChild(--i).getText());
        mods.push(this.visitVariableDeclaratorList(ctx.variableDeclaratorList()));

        return (mods.filter(i => i).join(' ') + ctx.getChild(i + 2).getText());
    }

    visitFieldModifier(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitPrimitiveType(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitAssignment(ctx) {
        if (!ctx) return '';

        return (this.visitIncOrDecExpression(ctx.incOrDecExpression())
            || Array.from({length: ctx.getChildCount() - 1})
                .map((_, i) => (
                    ctx.getChild(i).getText()
                )).join(' ') + ' ' + this.visitExpression(ctx.expression()));
    }

    visitVariableDeclaratorList(ctx) {
        if (!ctx) return '';

        return (
            Array.from({length: ctx.getChildCount()})
                .map((_, i) => (
                    this.visitVariableDeclarator(ctx.variableDeclarator(i))
                ))
                .filter(i => i)
                .join(', ')
        );
    }

    visitVariableDeclarator(ctx) {
        if (!ctx) return '';

        return ([
            this.visitVariableDeclaratorId(ctx.variableDeclaratorId()),
            ctx.getChild(1),
            this.visitVariableInitializer(ctx.variableInitializer()),
        ].filter(i => i).join(' '));
    }

    visitVariableDeclaratorId(ctx) {
        if (!ctx) return '';

        return (
            ctx.getText()
        );
    }

    visitVariableInitializer(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitMethodDeclaration(ctx) {
        if (!ctx) return '';

        let imp, i = 0, mods = [];
        while (imp = this.visitMethodModifier(ctx.methodModifier(i++))) {
            mods.push(imp);
        }

        mods.push(this.visitMethodHeader(ctx.methodHeader()));
        mods.push(this.visitMethodBody(ctx.methodBody()));

        return ('\n' + this.getTabs() + mods.filter(i => i).join(' '));
    }

    visitMethodModifier(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitMethodHeader(ctx) {
        if (!ctx) return '';

        return (
            ctx.getChild(0).getText()
            + ' '
            + this.visitMethodDeclarator(ctx.methodDeclarator())
        );
    }

    visitMethodDeclarator(ctx) {
        if (!ctx) return '';

        return ([
            ctx.getChild(0).getText(),
            ctx.getChild(1).getText(),
            this.visitFormalParameterList(ctx.formalParameterList()),
            ctx.getChild(ctx.getChildCount() - 1).getText(),
        ].join(''));
    }

    visitFormalParameterList(ctx) {
        if (!ctx) return '';

        let cur, args = [];
        for (let i = 0; i < ctx.getChildCount(); i++) {
            cur = this.visitFormalParameter(ctx.formalParameter(i))
            cur && args.push(cur);
        }

        return (args.join(', '));
    }

    visitFormalParameter(ctx) {
        if (!ctx) return '';

        return Array.from({length: ctx.getChildCount()})
            .map((_, ind) => ctx.getChild(ind).getText())
            .join(' ')
    }

    visitMethodBody(ctx) {
        if (!ctx) return '';

        return this.visitBlock(ctx.block()) || ctx.getText();
    }

    visitBlock(ctx) {
        if (!ctx) return '';

        let imp, i = 0, mods = [ctx.getChild(0).getText()];
        this.tabs++;
        while (imp = this.visitStatement(ctx.statement(i++))) {
            mods.push(this.getTabs() + imp);
        }
        this.tabs--;
        mods.push(this.getTabs() + ctx.getChild(ctx.getChildCount() - 1).getText());

        return (mods.filter(i => i).join('\n'));
    }

    visitConstructorDeclaration(ctx) {
        if (!ctx) return '';

        let imp, i = 0, mods = [];
        while (imp = this.visitMethodModifier(ctx.constructorModifier(i++))) {
            mods.push(imp);
        }

        mods.push(this.visitConstructorDeclarator(ctx.constructorDeclarator()));
        mods.push(this.visitConstructorBody(ctx.constructorBody()));

        return ('\n' + this.getTabs() + mods.filter(i => i).join(' '));
    }

    visitConstructorModifier(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitConstructorDeclarator(ctx) {
        if (!ctx) return '';

        return [
            ctx.getChild(0).getText(),
            ctx.getChild(1).getText(),
            this.visitFormalParameterList(ctx.formalParameterList()),
            ctx.getChild(ctx.getChildCount() - 1).getText(),
        ].filter(i => i).join('');
    }

    visitConstructorBody(ctx) {
        if (!ctx) return '';

        return (
            ctx.getChild(0).getText()
            + '\n' + this.getTabs() + this.getTabs()
            + this.visitExplicitConstructorInvocation(ctx.explicitConstructorInvocation())
            + this.visitStatement(ctx.statement())
            + '\n' + this.getTabs()
            + ctx.getChild(ctx.getChildCount() - 1).getText()
        );
    }

    visitExplicitConstructorInvocation(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitStatement(ctx) {
        if (!ctx || ctx.length === 0) return '';

        return (
            this.visitLocalVariableDeclaration(ctx.localVariableDeclaration())
            || this.visitAssignment(ctx.assignment())
            || this.visitStatementWithoutTrailingSubstatement(ctx.statementWithoutTrailingSubstatement())
            || this.visitIfStatement(ctx.ifStatement())
            || this.visitWhileStatement(ctx.whileStatement())
            || this.visitForStatement(ctx.forStatement())
        );
    }

    visitIfStatement(ctx) {
        if (!ctx) return '';

        let base = (
            ctx.getChild(0).getText()
            + ' '
            + ctx.getChild(1).getText()
            + this.visitExpression(ctx.expression())
            + ctx.getChild(3).getText()
            + ' '
        )

        const block = this.visitBlock(ctx.block(0));
        if (block) {
            base += block;
        } else {
            this.tabs++;
            base += "{\n" + this.getTabs() + this.visitStatement(ctx.statement(0))
            this.tabs--;
            base += "\n" + this.getTabs() + "}"
        }

        if (ctx.getChildCount() > 5) {
            base += ' ' + ctx.getChild(5).getText() + ' ';
        }

        base += this.visitBlock(ctx.block(1)) || this.visitStatement(ctx.statement(1));

        return (base);
    }

    visitWhileStatement(ctx) {
        if (!ctx) return '';

        let base = (
            ctx.getChild(0).getText()
            + ' '
            + ctx.getChild(1).getText()
            + this.visitExpression(ctx.expression())
            + ctx.getChild(3).getText()
        )

        let blockState = '';
        const block = this.visitBlock(ctx.block());
        if (block) {
            blockState += block;
        } else {
            this.tabs++;
            blockState += "{\n" + this.getTabs() + this.visitStatement(ctx.statement())
            this.tabs--;
            blockState += "\n" + this.getTabs() + "}"
        }

        base += blockState ? ' ' + blockState : ctx.getChild(4).getText();

        return (base);
    }

    visitForStatement(ctx) {
        if (!ctx) return '';

        let ind = 0;
        const decl = this.visitLocalVariableDeclaration(ctx.localVariableDeclaration());
        let base = (
            ctx.getChild(ind++).getText() + ' '
            + ctx.getChild(ind++).getText()
            + (decl ? decl : ctx.getChild(ind).getText())
        )
        ind++;

        const expr = this.visitExpression(ctx.expression());
        base += ' '
        if (expr) {
            base += expr;
            ind++;
        }
        base += ctx.getChild(ind++).getText()

        const incOrDec = this.visitIncOrDecExpression(ctx.incOrDecExpression());
        base += ' '
        if (incOrDec) {
            base += incOrDec;
            ind++;
        }
        base += ctx.getChild(ind++).getText() + ' '
        base += this.visitBlock(ctx.block())
        return (base);
    }

    visitLocalVariableDeclaration(ctx) {
        if (!ctx) return '';

        let imp, i = 0, mods = [];
        while (imp = this.visitVariableModifier(ctx.variableModifier(i++))) {
            mods.push(imp);
        }

        mods.push(ctx.getChild(--i).getText());
        mods.push(this.visitVariableDeclaratorList(ctx.variableDeclaratorList()));

        return (mods.join(' ') + ctx.getChild(i + 2).getText());
    }

    visitVariableModifier(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitStatementWithoutTrailingSubstatement(ctx) {
        if (!ctx) return '';

        return (
            this.visitReturnStatement(ctx.returnStatement())
            || this.visitBreakStatement(ctx.breakStatement())
            || this.visitContinueStatement(ctx.continueStatement())
            || this.visitExpressionStatement(ctx.expressionStatement())
        );
    }

    visitReturnStatement(ctx) {
        if (!ctx) return '';

        const expr = this.visitExpression(ctx.expression());

        return (
            ctx.getChild(0).getText()
            + (expr ? (' ' + expr + ctx.getChild(2)) : ctx.getChild(1))
        );
    }

    visitBreakStatement(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitContinueStatement(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitExpressionStatement(ctx) {
        if (!ctx) return '';

        return (
            this.visitStatementExpression(ctx.statementExpression())
            + ctx.getChild(1)
        );
    }

    visitStatementExpression(ctx) {
        if (!ctx) return '';

        return (
            this.visitAssignment(ctx.assignment())
            || this.visitMethodInvocation(ctx.methodInvocation())
            || this.visitClassInstanceCreationExpression(ctx.classInstanceCreationExpression())
            || ctx.getText()
        );
    }

    conditionalExpression(ctx) {
        if (!ctx) return '';

        return (
            ctx.getText()
        );
    }

    visitMethodInvocation(ctx) {
        if (!ctx) return '';

        return (
            ctx.getText()
        );
    }

    visitClassInstanceCreationExpression(ctx) {
        if (!ctx) return '';

        return (
            ctx.getChild(0).getText()
            + ' '
            + Array.from({length: ctx.getChildCount() - 1})
                .map((_, i) => ctx.getChild(i + 1).getText())
                .reduce((acc, el) => acc + el)
        );
    }

    visitExpression(ctx) {
        if (!ctx) return '';

        return (
            this.visitPrimitive(ctx.primitive())
            || this.visitConditionalExpression(ctx.conditionalExpression())
            || this.visitClassInstanceCreationExpression(ctx.classInstanceCreationExpression())
        );
    }

    visitPrimitive(ctx) {
        if (!ctx) return '';

        return ctx.getText();
    }

    visitConditionalExpression(ctx) {
        if (!ctx) return '';

        const expr = this.visitConditionalExpression(ctx.conditionalExpression());

        return (
            expr
                ? [
                    expr,
                    ctx.getChild(1).getText(),
                    this.visitExpression(ctx.expression()),
                    ctx.getChild(3).getText(),
                    this.visitConditionalOrExpression(ctx.conditionalOrExpression())
                ].join(' ')
                : this.visitConditionalOrExpression(ctx.conditionalOrExpression())
        );
    }

    visitConditionalOrExpression(ctx) {
        if (!ctx) return '';

        const expr = this.visitConditionalOrExpression(ctx.conditionalOrExpression());

        return (
            expr
                ? [
                    expr,
                    ctx.getChild(1).getText(),
                    this.visitConditionalAndExpression(ctx.conditionalAndExpression())
                ].join(' ')
                : this.visitConditionalAndExpression(ctx.conditionalAndExpression())
        );
    }

    visitConditionalAndExpression(ctx) {
        if (!ctx) return '';

        const expr = this.visitConditionalAndExpression(ctx.conditionalAndExpression());

        return (
            expr
                ? [
                    expr,
                    ctx.getChild(1).getText(),
                    this.visitRelationalExpression(ctx.relationalExpression())
                ].join(' ')
                : this.visitRelationalExpression(ctx.relationalExpression())
        );
    }

    visitRelationalExpression(ctx) {
        if (!ctx) return '';

        const expr = this.visitRelationalExpression(ctx.relationalExpression());

        return (
            expr
                ? [
                    expr,
                    ctx.getChild(1).getText(),
                    this.visitAdditiveExpression(ctx.additiveExpression())
                ].join(' ')
                : this.visitAdditiveExpression(ctx.additiveExpression())
        );
    }

    visitAdditiveExpression(ctx) {
        if (!ctx) return '';

        const expr = this.visitAdditiveExpression(ctx.additiveExpression());

        return (
            expr
                ? [
                    expr,
                    ctx.getChild(1).getText(),
                    this.visitMultiplicativeExpression(ctx.multiplicativeExpression())
                ].join(' ')
                : this.visitMultiplicativeExpression(ctx.multiplicativeExpression())
        );
    }

    visitMultiplicativeExpression(ctx) {
        if (!ctx) return '';

        const expr = this.visitMultiplicativeExpression(ctx.multiplicativeExpression());

        return (
            expr
                ? [
                    expr,
                    ctx.getChild(1).getText(),
                    this.visitUnaryExpression(ctx.unaryExpression())
                ].join(' ')
                : this.visitUnaryExpression(ctx.unaryExpression())
        );
    }

    visitUnaryExpression(ctx) {
        if (!ctx) return '';

        const expr = this.visitUnaryExpression(ctx.unaryExpression());

        return (
            expr
                ? [
                    ctx.getChild(0).getText(),
                    this.visitUnaryExpression(ctx.unaryExpression())
                ].join('')
                : this.visitIncOrDecExpression(ctx.incOrDecExpression())
        );
    }

    visitIncOrDecExpression(ctx) {
        if (!ctx) return '';

        const expr = this.visitExpression(ctx.expression())

        return (
            this.visitPrimitive(ctx.primitive())
            || (expr
                    ? (
                        ctx.getChild(0).getText()
                        + expr
                        + ctx.getChild(2).getText()
                    )
                    : ctx.getText()
            ));
    }
}

tree.accept(new Visitor());

// console.log(pretty)

fs.writeFileSync(__dirname + "\\TestPretty.java", pretty, "utf-8");


// ParseTreeWalker.DEFAULT.walk(new Listener(parser), tree);


// console.log(tree.toStringTree(parser.ruleNames))
// console.log(tree)