parser grammar ExpressionParser;
options { tokenVocab = ExpressionLexer; }

expression
    : constant
    | chainable
    | expression LBRACK expression RBRACK
    | expression (MUL | DIV | MOD) expression
    | expression (ADD | SUB) expression
    | SUB expression
    | expression (EQ | NOTEQUAL | GT | GE | LT | LE) expression
    | BANG expression
    | expression (AND | OR) expression
    | LPAREN expression RPAREN
    ;

constant
    : STRING_LITERAL
    | BOOLEAN_LITERAL
    | DECIMAL_LITERAL
    | FLOAT_LITERAL
    ;

chainable
    : IDENTIFIER                                       //variable
    | chainable DOT IDENTIFIER                         //property
    | chainable DOT IDENTIFIER ASSIGN expression       //assign property
    | IDENTIFIER ASSIGN expression                     //assign variable
    | chainable DOT IDENTIFIER arguments               //method
    | IDENTIFIER arguments                             //function
    ;

arguments
    : LPAREN (expression (COMMA expression)*)? RPAREN
    ;