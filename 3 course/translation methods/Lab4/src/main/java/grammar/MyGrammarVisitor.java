// Generated from C:/Users/Vlad/Desktop/Translation-Methods-main/Lab4/src/main/java/grammar\MyGrammar.g4 by ANTLR 4.9.2
package grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MyGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MyGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#run}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRun(MyGrammarParser.RunContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#grammarName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammarName(MyGrammarParser.GrammarNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#fields}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFields(MyGrammarParser.FieldsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#grammarRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammarRule(MyGrammarParser.GrammarRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#terminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal(MyGrammarParser.TerminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#nonTerminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonTerminal(MyGrammarParser.NonTerminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#rulE}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRulE(MyGrammarParser.RulEContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#triple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTriple(MyGrammarParser.TripleContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(MyGrammarParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#primitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitive(MyGrammarParser.PrimitiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCode(MyGrammarParser.CodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#attr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttr(MyGrammarParser.AttrContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#inheritedAttr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInheritedAttr(MyGrammarParser.InheritedAttrContext ctx);
}