// Generated from com/vasnatech/commons/expression/antlr/ExpressionParser.g4 by ANTLR 4.12.0
package com.vasnatech.commons.expression.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(ExpressionParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(ExpressionParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#chainable}.
	 * @param ctx the parse tree
	 */
	void enterChainable(ExpressionParser.ChainableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#chainable}.
	 * @param ctx the parse tree
	 */
	void exitChainable(ExpressionParser.ChainableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(ExpressionParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(ExpressionParser.ArgumentsContext ctx);
}