package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;

public class Generator {

	public String generate(AST ast) {
		StringBuilder stringBuilder = new StringBuilder();
		for(ASTNode node : ast.root.body) {
			if(node instanceof Stylerule) {
				stringBuilder.append(printStylerule((Stylerule) node));
			}
		}

		return stringBuilder.toString();
	}

	private String printStylerule(Stylerule node) {
		StringBuilder stringBuilder = new StringBuilder();
		for(ASTNode child : node.getChildren()) {
			if(child instanceof Selector) {
				stringBuilder.append(printSelector((Selector) child)).append(" {\n");
			}
			else if(child instanceof Declaration) {
				stringBuilder.append("\t").append(printDeclaration((Declaration) child)).append(";\n");
			}
		}
		stringBuilder.append("}\n");
		return stringBuilder.toString();
	}

	private String printSelector(Selector node) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(node);
		return stringBuilder.toString();
	}

	private String printDeclaration(Declaration node) {
		StringBuilder stringBuilder = new StringBuilder();
		for(ASTNode child : node.getChildren()) {
			if(child instanceof PropertyName) {
				stringBuilder.append(printPropertyName((PropertyName) child)).append(": ");
			}
			else if(child instanceof Literal) {
				stringBuilder.append(printLiteral((Literal) child));
			}
		}
		return stringBuilder.toString();
	}

	private String printPropertyName(PropertyName node) {
		return node.name;
	}

	private String printLiteral(Literal literal) {
		if(literal instanceof ColorLiteral) {
			return printColor((ColorLiteral) literal);
		}
		else if(literal instanceof PercentageLiteral) {
			return printPercentage((PercentageLiteral) literal);
		}
		else if(literal instanceof PixelLiteral) {
			return printPixel((PixelLiteral) literal);
		}
		else if(literal instanceof ScalarLiteral) {
			return printScalar((ScalarLiteral) literal);
		}
		else {
			return "";
		}
	}

	private String printColor(ColorLiteral literal) {
		return literal.value;
	}

	private String printPercentage(PercentageLiteral literal) {
		return String.valueOf(literal.value);
	}

	private String printPixel(PixelLiteral literal) {
		return String.valueOf(literal.value);
	}

	private String printScalar(ScalarLiteral literal) {
		return String.valueOf(literal.value);
	}


}
