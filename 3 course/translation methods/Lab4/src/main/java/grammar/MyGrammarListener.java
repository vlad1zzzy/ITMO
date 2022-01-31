// Generated from C:/Users/Vlad/Desktop/Translation-Methods-main/Lab4/src/main/java/grammar\MyGrammar.g4 by ANTLR 4.9.2
package grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MyGrammarParser}.
 */
public interface MyGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#run}.
	 * @param ctx the parse tree
	 */
	void enterRun(MyGrammarParser.RunContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#run}.
	 * @param ctx the parse tree
	 */
	void exitRun(MyGrammarParser.RunContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#grammarName}.
	 * @param ctx the parse tree
	 */
	void enterGrammarName(MyGrammarParser.GrammarNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#grammarName}.
	 * @param ctx the parse tree
	 */
	void exitGrammarName(MyGrammarParser.GrammarNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#fields}.
	 * @param ctx the parse tree
	 */
	void enterFields(MyGrammarParser.FieldsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#fields}.
	 * @param ctx the parse tree
	 */
	void exitFields(MyGrammarParser.FieldsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#grammarRule}.
	 * @param ctx the parse tree
	 */
	void enterGrammarRule(MyGrammarParser.GrammarRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#grammarRule}.
	 * @param ctx the parse tree
	 */
	void exitGrammarRule(MyGrammarParser.GrammarRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#terminal}.
	 * @param ctx the parse tree
	 */
	void enterTerminal(MyGrammarParser.TerminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#terminal}.
	 * @param ctx the parse tree
	 */
	void exitTerminal(MyGrammarParser.TerminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#nonTerminal}.
	 * @param ctx the parse tree
	 */
	void enterNonTerminal(MyGrammarParser.NonTerminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#nonTerminal}.
	 * @param ctx the parse tree
	 */
	void exitNonTerminal(MyGrammarParser.NonTerminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#rulE}.
	 * @param ctx the parse tree
	 */
	void enterRulE(MyGrammarParser.RulEContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#rulE}.
	 * @param ctx the parse tree
	 */
	void exitRulE(MyGrammarParser.RulEContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#triple}.
	 * @param ctx the parse tree
	 */
	void enterTriple(MyGrammarParser.TripleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#triple}.
	 * @param ctx the parse tree
	 */
	void exitTriple(MyGrammarParser.TripleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(MyGrammarParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(MyGrammarParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#primitive}.
	 * @param ctx the parse tree
	 */
	void enterPrimitive(MyGrammarParser.PrimitiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#primitive}.
	 * @param ctx the parse tree
	 */
	void exitPrimitive(MyGrammarParser.PrimitiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#code}.
	 * @param ctx the parse tree
	 */
	void enterCode(MyGrammarParser.CodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#code}.
	 * @param ctx the parse tree
	 */
	void exitCode(MyGrammarParser.CodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#attr}.
	 * @param ctx the parse tree
	 */
	void enterAttr(MyGrammarParser.AttrContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#attr}.
	 * @param ctx the parse tree
	 */
	void exitAttr(MyGrammarParser.AttrContext ctx);
	/**
	 * Enter a parse tree produced by {@link MyGrammarParser#inheritedAttr}.
	 * @param ctx the parse tree
	 */
	void enterInheritedAttr(MyGrammarParser.InheritedAttrContext ctx);
	/**
	 * Exit a parse tree produced by {@link MyGrammarParser#inheritedAttr}.
	 * @param ctx the parse tree
	 */
	void exitInheritedAttr(MyGrammarParser.InheritedAttrContext ctx);
}