/**
 * 
 */
package com.expr.brule.edits;

import com.expr.brule.common.ParseWrapper;
import com.expr.brule.core.BusinessRuleParser.EnclosedExpressionContext;
import com.expr.brule.core.BusinessRuleParser.ExprContext;
import com.expr.brule.core.BusinessRuleParser.LogicalExpressionContext;
import com.expr.brule.core.BusinessRuleParser.NumberExpressionContext;
import com.expr.brule.core.BusinessRuleParser.ParseContext;
import com.expr.brule.core.BusinessRuleParser.StringExpressionContext;
import com.expr.brule.core.BusinessRuleParser.VariableExpressionContext;

/**
 * @author ssdImmanuel
 *
 *Checks and inserts parenthesis as per the Grammar
 *
 */
public class RuleBalancer extends ParseWrapper {

	/**
	 * @param rule
	 */
	public RuleBalancer(String rule) {
		super(rule);
	}

	private int openInsertCount = 0;
	private int closeInsertCount = 0;

	@Override
	public void enterEnclosedExpression(EnclosedExpressionContext ctx) {
		if (!ctx.start.getText().equals("(")) {
			rw.insertBefore(ctx.start, " ( ");
			openInsertCount++;
		}

		if (!ctx.stop.getText().equals(")")) {
			rw.insertAfter(ctx.stop, " ) ");
			closeInsertCount++;
		}
	}

	@Override
	public void enterLogicalExpression(LogicalExpressionContext ctx) {
		if (ctx.getParent() instanceof EnclosedExpressionContext) {
			return;
		}
		if(ctx.getParent() instanceof ParseContext) {
			handleBalancingForSingleExpression(ctx);
			return;
		}
		rw.insertBefore(ctx.start, " ( ");
		openInsertCount++;
		rw.insertAfter(ctx.stop, " ) ");
		closeInsertCount++;
	}

	@Override
	public void enterVariableExpression(VariableExpressionContext ctx) {
		if (ctx.getParent() instanceof EnclosedExpressionContext) {
			return;
		}
		
		if(ctx.getParent() instanceof ParseContext) {
			handleBalancingForSingleExpression(ctx);
			return;
		}
		
		if (!ctx.start.getText().equals("(")) {
			rw.insertBefore(ctx.start, " ( ");
			openInsertCount++;
		}

		if (!ctx.stop.getText().equals(")")) {
			rw.insertAfter(ctx.stop, " ) ");
			closeInsertCount++;
		}
	}

	@Override
	public void enterStringExpression(StringExpressionContext ctx) {
		if (ctx.getParent() instanceof EnclosedExpressionContext) {
			return;
		}
		
		if(ctx.getParent() instanceof ParseContext) {
			handleBalancingForSingleExpression(ctx);
			return;
		}
		
		if (!ctx.start.getText().equals("(")) {
			rw.insertBefore(ctx.start, " ( ");
			openInsertCount++;
		}

		if (!ctx.stop.getText().equals(")")) {
			rw.insertAfter(ctx.stop, " ) ");
			closeInsertCount++;
		}
	}

	@Override
	public void enterNumberExpression(NumberExpressionContext ctx) {
		if (ctx.getParent() instanceof EnclosedExpressionContext) {
			return;
		}
		
		if(ctx.getParent() instanceof ParseContext) {
			handleBalancingForSingleExpression(ctx);
			return;
		}
		
		if (!ctx.start.getText().equals("(")) {
			rw.insertBefore(ctx.start, " ( ");
			openInsertCount++;
		}

		if (!ctx.stop.getText().equals(")")) {
			rw.insertAfter(ctx.stop, " ) ");
			closeInsertCount++;
		}
	}
	
	private void handleBalancingForSingleExpression(ExprContext ctx) {
		ParseContext ctxp = (ParseContext) ctx.getParent();
		//System.out.println("start:"+ctxp.start.getText());
		if (!ctxp.start.getText().equals("(")) {
			rw.insertBefore(ctx.start, " ( ");
			openInsertCount++;
		}

		if (!ctxp.stop.getText().equals(")")) {
			rw.insertAfter(ctx.stop, " ) ");
			closeInsertCount++;
		}
	}

	public int getOpenInsertCount() {
		return openInsertCount;
	}

	public void setOpenInsertCount(int openInsertCount) {
		this.openInsertCount = openInsertCount;
	}

	public int getCloseInsertCount() {
		return closeInsertCount;
	}

	public void setCloseInsertCount(int closeInsertCount) {
		this.closeInsertCount = closeInsertCount;
	}

}
