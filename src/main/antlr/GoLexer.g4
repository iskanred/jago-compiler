lexer grammar GoLexer;

options {
   superClass = inno.jago.GoLexerBase;
}


//* ===== Fragments ===== *//


/* Basic symbols */
fragment NEW_LINE                   :   [\n\r];
fragment UNICODE_LETTER             :   [\p{L}][\p{M}]*;
fragment UNICODE_DIGIT              :   [\p{Nd}];
fragment UNICODE_CHAR               :   ~[\n\r]; // not a NEW_LINE


/* Letters, digits, exponents */
fragment LETTER                     :   UNICODE_LETTER | '_';

fragment DECIMAL_DIGIT              :   [0-9];
fragment BINARY_DIGIT               :   [01];
fragment OCTAL_DIGIT                :   [0-7];
fragment HEX_DIGIT                  :   [0-9] | [A-F] | [a-f];

fragment DECIMAL_DIGITS             :   DECIMAL_DIGIT ('_'? DECIMAL_DIGIT)*;
fragment BINARY_DIGITS              :   BINARY_DIGIT ('_'? BINARY_DIGIT)*;
fragment OCTAL_DIGITS               :   OCTAL_DIGIT ('_'? OCTAL_DIGIT)*;
fragment HEX_DIGITS                 :   HEX_DIGIT ('_'? HEX_DIGIT)*;

fragment DECIMAL_EXPONENT           :   [eE] [+-]? DECIMAL_DIGITS;
fragment HEX_EXPONENT               :   [pP] ('+' | '-' )? DECIMAL_DIGITS;
fragment HEX_MANTISSA               :   ('_')? HEX_DIGITS '.' (HEX_DIGITS)?
                                    |   ('_')? HEX_DIGITS
                                    |   '.' HEX_DIGITS
                                    ;

/* Unicode symbols encoded as */
fragment UNICODE_ENCODED_VALUE      :   LITTLE_U_VALUE | BIG_U_VALUE | ESCAPED_CHAR;

fragment BYTE_VALUE                 :   OCTAL_BYTE_VALUE | HEX_BYTE_VALUE;

fragment OCTAL_BYTE_VALUE           :   '\\' OCTAL_DIGIT OCTAL_DIGIT OCTAL_DIGIT
                                        /* UTF-8 range maintenance */
                                        {
                                            Integer.parseInt(getText().substring(getText().indexOf('\\') + 1), 8) < Integer.parseInt("256")
                                        }?
                                    ;

fragment HEX_BYTE_VALUE             :   '\\' 'x' HEX_DIGIT HEX_DIGIT;


fragment LITTLE_U_VALUE             :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT // 4
                                        /* UTF-16 surrogate halves maintenance */
                                        {
                                            Integer.parseInt(getText().substring(getText().indexOf('u') + 1), 16) < Integer.parseInt("D800", 16)
                                             ||
                                            Integer.parseInt(getText().substring(getText().indexOf('u') + 1), 16) > Integer.parseInt("DFFF", 16)
                                        }?
                                    ;

fragment BIG_U_VALUE                :   '\\' 'U' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
                                                 HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT // 8
                                        /* UTF-32 range maintenance */
                                        {
                                            Integer.parseInt(getText().substring(getText().indexOf('U') + 1), 16) <= Integer.parseInt("10FFFF", 16)
                                        }?
                                    ;

fragment ESCAPED_CHAR               :   '\\' [abfnrtv\\'"]; // a b f n r t v \ ' "



//* ===== Basic Tokens ===== *//


/* Comments */
GENERAL_COMMENT                     :   '/*' .*? '*/' -> channel(HIDDEN);
LINE_COMMENT                        :   '//' ~[\r\n]* -> channel(HIDDEN);


/* End of statement */
END                                 :   SEMI
                                    |   NEW_LINE
                                        {
                                            getLastTokenType() == IDENTIFIER    ||

                                            getLastTokenType() == INT_LIT       ||
                                            getLastTokenType() == FLOAT_LIT     ||
                                            getLastTokenType() == IMAGINARY_LIT ||
                                            getLastTokenType() == RUNE_LIT      ||
                                            getLastTokenType() == STRING_LIT    ||

                                            getLastTokenType() == BREAK         ||
                                            getLastTokenType() == CONTINUE      ||
                                            getLastTokenType() == FALLTHROUGH   ||
                                            getLastTokenType() == RETURN        ||

                                            getLastTokenType() == PLUS_PLUS     ||
                                            getLastTokenType() == MINUS_MINUS   ||
                                            getLastTokenType() == R_PAREN       ||
                                            getLastTokenType() == R_BRACKET     ||
                                            getLastTokenType() == R_CURLY
                                        }?;


/* Whitespace characters */
WHITESPACE                          :   (NEW_LINE
                                    |    [\t]
                                    |    [\u0020]   // ' '
                                    |    [\r])
                                    -> channel(HIDDEN)
                                    ;


/* Keywords */
BREAK                               :   'break';
DEFAULT                             :   'default';
FUNC                                :   'func';
INTERFACE                           :   'interface';
SELECT                              :   'select';
CASE                                :   'case';
DEFER                               :   'defer';
GO                                  :   'go';
MAP                                 :   'map';
STRUCT                              :   'struct';
CHAN                                :   'chan';
ELSE                                :   'else';
GOTO                                :   'goto';
PACKAGE                             :   'package';
SWITCH                              :   'switch';
CONST                               :   'const';
FALLTHROUGH                         :   'fallthrough';
IF                                  :   'if';
RANGE                               :   'range';
TYPE                                :   'type';
CONTINUE                            :   'continue';
FOR                                 :   'for';
IMPORT                              :   'import';
RETURN                              :   'return';
VAR                                 :   'var';


/* Punctuations & Operators */
L_PAREN                             :   '(';
R_PAREN                             :   ')';
L_CURLY                             :   '{';
R_CURLY                             :   '}';
L_BRACKET                           :   '[';
R_BRACKET                           :   ']';
ASSIGN                              :   '=';
COMMA                               :   ',';
SEMI                                :   ';';
COLON                               :   ':';
DOT                                 :   '.';
PLUS_PLUS                           :   '++';
MINUS_MINUS                         :   '--';
DECLARE_ASSIGN                      :   ':=';
ELLIPSIS                            :   '...';
LOGICAL_OR                          :   '||';
LOGICAL_AND                         :   '&&';
EQUALS                              :   '==';
NOT_EQUALS                          :   '!=';
LESS                                :   '<';
LESS_OR_EQUALS                      :   '<=';
GREATER                             :   '>';
GREATER_OR_EQUALS                   :   '>=';
PLUS                                :   '+';
MINUS                               :   '-';
CARET                               :   '^';
ASTERISK                            :   '*';
AMPERSAND                           :   '&';
OR                                  :   '|';
DIV                                 :   '/';
MOD                                 :   '%';
LSHIFT                              :   '<<';
RSHIFT                              :   '>>';
BIT_CLEAR                           :   '&^';
EXCLAMATION                         :   '!';
RECEIVE                             :   '<-';


/* Identifier */
IDENTIFIER                          :   LETTER (LETTER | UNICODE_DIGIT)*;



//* ===== Literal Tokens ===== *//


/* Integer literal */
INT_LIT                             :   DECIMAL_LIT | BINARY_LIT | OCTAL_LIT | HEX_LIT;

fragment DECIMAL_LIT                :   '0' | [1-9] ('_'? DECIMAL_DIGITS)?;
fragment BINARY_LIT                 :   '0' [bB] '_'? BINARY_DIGITS;
fragment OCTAL_LIT                  :   '0' [oO]? '_'?  OCTAL_DIGITS;
fragment HEX_LIT                    :   '0' [xX] '_'? HEX_DIGITS;


/* Floating point literal */
FLOAT_LIT                           :   DECIMAL_FLOAT_LIT | HEX_FLOAT_LIT;

fragment DECIMAL_FLOAT_LIT          :   DECIMAL_DIGITS '.' DECIMAL_DIGITS? DECIMAL_EXPONENT?
                                    |   DECIMAL_DIGITS DECIMAL_EXPONENT
                                    |   '.' DECIMAL_DIGITS DECIMAL_EXPONENT?
                                    ;

fragment HEX_FLOAT_LIT              :   '0' [xX] HEX_MANTISSA HEX_EXPONENT;


/* Imaginary literal */
IMAGINARY_LIT                       :   (DECIMAL_DIGITS | INT_LIT | FLOAT_LIT) 'i';

/* Rune literal */
RUNE_LIT                            :   '\'' (~[\r\n'] | UNICODE_ENCODED_VALUE | BYTE_VALUE) '\'';



/* String literal */
STRING_LIT                          :   RAW_STRING_LIT | INTERPRETED_STRING_LIT;

fragment RAW_STRING_LIT             :   '`' (~[`] | NEW_LINE)* '`';

fragment INTERPRETED_STRING_LIT     :   '"' (~[\r\n"\\] | UNICODE_ENCODED_VALUE | BYTE_VALUE)* '"';
