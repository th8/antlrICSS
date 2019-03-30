package nl.han.ica.icss.checker;

import java.util.HashMap;
import java.util.LinkedList;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.*;

public class Checker {

    private LinkedList<HashMap<String,ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new LinkedList<>();

        checkChildren(ast.root);
    }

    private void checkChildren(ASTNode node) {
        for(ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment) {
            }

            if(child.getChildren().size() > 0) {
                checkChildren(child);
            }

        }
    }
}
