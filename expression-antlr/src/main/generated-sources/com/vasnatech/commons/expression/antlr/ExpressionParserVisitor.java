// Generated from com/vasnatech/commons/expression/antlr/ExpressionParser.g4 by ANTLR 4.12.0
package com.vasnatech.commons.expression.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(ExpressionParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#chainable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChainable(ExpressionParser.ChainableContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(ExpressionParser.ArgumentsContext ctx);
}