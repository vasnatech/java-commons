lexer grammar ExpressionLexer;

DECIMAL_LITERAL
    : ('0' | [1-9] (Digits? | '_'+ Digits)) [lL]?
    ;

FLOAT_LITERAL
    : (Digits '.' Digits? | '.' Digits) [fFdD]?
    | Digits [fFdD]?
    ;

BOOLEAN_LITERAL
    : 'true'
    | 'false'
    ;

STRING_LITERAL
    : QUOTE (~["\\\r\n] | EscapeSequence)* QUOTE
    ;

LPAREN:             '(';
RPAREN:             ')';
LBRACK:             '[';
RBRACK:             ']';
COMMA:              ',';
DOT:                '.';
QUOTE:              '\'' ;

AND:                '&&';
OR:                 '||';
BANG:               '!';
ASSIGN:             '=';
EQ :                '==';
NOTEQUAL:           '!=';
GT:                 '>';
LT:                 '<';
LE:                 '<=';
GE:                 '>=';

ADD:                '+';
SUB:                '-';
MUL:                '*';
DIV:                '/';
MOD:                '%';

WS:                 [ \t\r\n\u000C]+ -> channel(HIDDEN);

IDENTIFIER:         Letter LetterOrDigit*;


fragment Digits
    : [0-9] ([0-9_]* [0-9])?
    ;

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;

fragment Letter
    : [a-zA-Z$_] // these are the "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;

fragment EscapeSequence
    : '\\' 'u005c'? [btnfr"'\\]
    | '\\' 'u005c'? ([0-3]? [0-7])? [0-7]
    | '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit
    ;

fragment HexDigit
    : [0-9a-fA-F]
    ;