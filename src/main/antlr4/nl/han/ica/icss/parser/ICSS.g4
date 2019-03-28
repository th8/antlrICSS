//LET OP:
//Dit is de bijgewerkte versie met handigere LOWER_IDENT en andere volgorde/prioriteit

grammar ICSS;

//--- LEXER: ---

OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';

//Literals
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;

//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//--- PARSER: ---

stylesheet: (variableAssignment | stylerule)+ EOF;
stylerule: selector OPEN_BRACE (stylerule | variableAssignment | declaration)+ CLOSE_BRACE;
declaration: propertyName COLON (literal | variableReference | operation) SEMICOLON;
selector: ID_IDENT | CLASS_IDENT | LOWER_IDENT;
propertyName: LOWER_IDENT;
literal: PIXELSIZE | PERCENTAGE | SCALAR | COLOR;
operation: (literal | variableReference | multiplyOperation) ((PLUS | MIN) (literal | variableReference | multiplyOperation))*;
multiplyOperation: (literal | variableReference) (MUL (literal | variableReference | multiplyOperation))*;
variableAssignment: variableReference ASSIGNMENT_OPERATOR (literal | variableReference | operation) SEMICOLON;
variableReference: CAPITAL_IDENT;
