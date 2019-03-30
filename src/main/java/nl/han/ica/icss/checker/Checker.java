package nl.han.ica.icss.checker;

import java.util.HashMap;
import java.util.LinkedList;

import com.google.errorprone.annotations.Var;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.*;

public class Checker {

    private LinkedList<HashMap<String,ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new LinkedList<>();

        checkChildren(ast.root);
    }

    //Recursively check each child for checker errors
    private void checkChildren(ASTNode node) {
        for(ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment) {
                variableTypes.add(setVariableAssignment((VariableAssignment) child));
            }
            else if(child instanceof VariableReference) {
                checkAssignment((VariableReference) child);
            }
            else if(child instanceof Operation) {
                checkOperationForColors((Operation) child);
                checkOperationOperands((Operation) child);
            }
            else if(child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            }

            if(child.getChildren().size() > 0) {
                checkChildren(child);
            }

        }
    }




    private void checkDeclaration(Declaration node) {
        //Check wether a non-color expression is assigned to a color property
        if(node.property.name.equals("color") || node.property.name.equals("background-color")) {
            if(node.expression instanceof Literal && !(node.expression instanceof ColorLiteral)) {
                node.setError("Non-color assignment to color property");
            }
            else if (node.expression instanceof Operation) {
                node.setError("Non-color assignment to color property");
            }
            else if (node.expression instanceof VariableReference) {
                if(checkAssignment((VariableReference) node.expression) != ExpressionType.COLOR) {
                    node.setError("Non-color assignment to color property");
                }
            }
        }
        //Check non-color properties for color expressions
        else {
            if(node.expression instanceof ColorLiteral) {
                node.setError("Color assignment to non-color property");
            }
            if(node.expression instanceof VariableReference) {
                if(checkAssignment((VariableReference) node.expression) == ExpressionType.COLOR) {
                    node.setError("Color assignment to non-color property");
                }
            }
        }
    }

    //Check operations for color literals and references
    private void checkOperationForColors(Operation node) {
        if(node.lhs instanceof ColorLiteral || node.rhs instanceof ColorLiteral) {
            node.setError("Can't do an operation on colors");
        }
        else if(node.lhs instanceof VariableReference) {
            if(checkAssignment((VariableReference) node.lhs) == ExpressionType.COLOR) {
                node.setError("Can't do an operation on colors");
            }
        }
        else if(node.rhs instanceof VariableReference) {
            if(checkAssignment((VariableReference) node.rhs) == ExpressionType.COLOR) {
                node.setError("Can't do an operation on colors");
            }
        }
    }

    private void checkOperationOperands(Operation node) {
        if(node instanceof AddOperation || node instanceof SubtractOperation) {
            checkOperationSameOperandtypes(node);
        }
        else if(node instanceof MultiplyOperation) {
            checkMultiplyOperandsScalar(node);
        }
    }

    private void checkOperationSameOperandtypes(Operation node) {
        ExpressionType lhsType = getExpressionType(node.lhs);
        ExpressionType rhsType = getExpressionType(node.rhs);
        System.out.println(lhsType + " " + rhsType);

        //Set error if types don't match
        if(lhsType != null && rhsType != null) {
            if(lhsType != rhsType) {
                node.setError("Non-matching types in add or substract operation");
            }
        }
    }

    private void checkMultiplyOperandsScalar(Operation node) {
        ExpressionType lhsType = getExpressionType(node.lhs);
        ExpressionType rhsType = getExpressionType(node.rhs);

        if(lhsType != ExpressionType.SCALAR && rhsType != ExpressionType.SCALAR) {
            node.setError("Non-scalar types in multiply operation");
        }
    }

    private ExpressionType getExpressionType(Expression node) {
        if(node instanceof VariableReference) {
            return checkAssignment((VariableReference) node);
        }
        else if(node instanceof Literal) {
            return getLiteralType((Literal) node);
        }
        return null;
    }

    private ExpressionType getLiteralType(Literal literal) {
        if(literal instanceof ColorLiteral) {
            return ExpressionType.COLOR;
        }
        else if(literal instanceof PercentageLiteral) {
            return ExpressionType.PERCENTAGE;
        }
        else if(literal instanceof PixelLiteral) {
            return ExpressionType.PIXEL;
        }
        else if(literal instanceof ScalarLiteral) {
            return ExpressionType.SCALAR;
        }
        else {
            return ExpressionType.UNDEFINED;
        }
    }

    //Check wether a variable has been assigned and return the type if assigned
    private ExpressionType checkAssignment(VariableReference node) {
        boolean doesExist = false;
        ExpressionType type = null;
        for(HashMap assignment : variableTypes) {
            if(assignment.containsKey(node.name)) {
                doesExist = true;
                type = (ExpressionType) assignment.get(node.name);
            }
        }
        if(!doesExist) {
            node.setError("Variable not defined.");
        }
        return type;
    }

    //Add assigned variables to the linked list
    private HashMap<String, ExpressionType> setVariableAssignment(VariableAssignment child) {
        HashMap<String, ExpressionType> assignment = new HashMap<String, ExpressionType>();
        if(child.expression instanceof ColorLiteral) {
            assignment.put(child.name.name, ExpressionType.COLOR);
        }
        else if(child.expression instanceof PercentageLiteral) {
            assignment.put(child.name.name, ExpressionType.PERCENTAGE);
        }
        else if(child.expression instanceof PixelLiteral) {
            assignment.put(child.name.name, ExpressionType.PIXEL);
        }
        else if(child.expression instanceof ScalarLiteral) {
            assignment.put(child.name.name, ExpressionType.SCALAR);
        }
        else {
            assignment.put(child.name.name, ExpressionType.UNDEFINED);
        }
        return assignment;
    }
}
