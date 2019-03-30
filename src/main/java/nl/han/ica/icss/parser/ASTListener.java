package nl.han.ica.icss.parser;

import java.util.Stack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private Stack<ASTNode> currentContainer;

	//Use this to keep track of the amount of operations pushed to the stack
	private int operationCount;

	public ASTListener() {
		ast = new AST();
		currentContainer = new Stack<>();
		operationCount = 0;
	}

	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet stylesheet = new Stylesheet();
		currentContainer.push(stylesheet);
		ast.setRoot(stylesheet);

	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		currentContainer.pop();
	}

	//Listen for Variable declarations and their references
	@Override
	public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		VariableAssignment assignment = new VariableAssignment();
		currentContainer.peek().addChild(assignment);
		currentContainer.push(assignment);
	}

	@Override
	public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
		VariableReference reference = new VariableReference(ctx.getText());
		currentContainer.peek().addChild(reference);
	}

	//Listen for stylerules and their contents
	@Override
	public void enterStylerule(ICSSParser.StyleruleContext ctx) {
		Stylerule rule = new Stylerule();
		currentContainer.peek().addChild(rule);
		currentContainer.push(rule);
	}

	@Override
	public void exitStylerule(ICSSParser.StyleruleContext ctx) {
		currentContainer.pop();
	}


	@Override
	public void exitSelector(ICSSParser.SelectorContext ctx) {
		Character identifier = ctx.getText().charAt(0);
		Selector selector;
		if(identifier.equals('.')) {
			selector = new ClassSelector(ctx.getText());
		} else if (identifier.equals('#')) {
			selector = new IdSelector(ctx.getText());
		} else {
			selector = new TagSelector(ctx.getText());
		}
		currentContainer.peek().addChild(selector);
	}

	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration declaration = new Declaration();
		currentContainer.peek().addChild(declaration);
		currentContainer.push(declaration);
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterPropertyName(ICSSParser.PropertyNameContext ctx) {
		PropertyName name = new PropertyName(ctx.getText());
		currentContainer.peek().addChild(name);
	}

	@Override
	public void exitLiteral(ICSSParser.LiteralContext ctx) {
		Character firstChar = ctx.getText().charAt(0);
		Character lastChar = ctx.getText().charAt(ctx.getText().length()-1);
		Literal literal;
		if(firstChar.equals('#')) {
			literal = new ColorLiteral(ctx.getText());
		} else if(lastChar.equals('x')) {
			literal = new PixelLiteral(ctx.getText());
		} else if(lastChar.equals('%')) {
			literal = new PercentageLiteral(ctx.getText());
		} else {
			literal = new ScalarLiteral(ctx.getText());
		}
		currentContainer.peek().addChild(literal);
	}

	@Override
	public void enterOperation(ICSSParser.OperationContext ctx) {
		Operation operation;
		String operator = ctx.getChild(1).getText();
		if (operator.equals("+")) {
			operation = new AddOperation();
			currentContainer.peek().addChild(operation);
			currentContainer.push(operation);
			operationCount++;
		} else if (operator.equals("-")) {
			operation = operation = new SubtractOperation();
			currentContainer.peek().addChild(operation);
			currentContainer.push(operation);
			operationCount++;
		}
	}

	@Override
	public void exitOperation(ICSSParser.OperationContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterTerm(ICSSParser.TermContext ctx) {
		Operation operation = new MultiplyOperation();
		currentContainer.peek().addChild(operation);
		currentContainer.push(operation);
	}

	@Override
	public void exitTerm(ICSSParser.TermContext ctx) {
		currentContainer.pop();
	}

    public AST getAST() {
        return ast;
    }
}
