parser grammar GoParser;

options {
    tokenVocab=GoLexer;
 }

sourceFile          :   packageClause ( END ) ( importDecl ( END ) )* ( topLevelDecl ( END ) )* EOF;


packageClause       :   PACKAGE packageName;
packageName         :   IDENTIFIER;

importDecl          :   IMPORT ( importSpec | L_PAREN ( importSpec END )* ( importSpec ( END )? )? R_PAREN );
importSpec          :   ( DOT | packageName )? importPath;
importPath          :   STRING_LIT;


topLevelDecl        :   declaration | functionDecl | methodDecl;

declaration         :   constDecl | typeDecl | varDecl;


functionDecl        :   FUNC functionName signature ( functionBody )?;
functionName        :   IDENTIFIER;
functionBody        :   block;


methodDecl          :   FUNC receiver methodName signature ( functionBody )?;
receiver            :   parameters;


constDecl           :   CONST ( constSpec | L_PAREN ( constSpec END )* ( constSpec ( END )? )? R_PAREN );
constSpec           :   identifierList ( ( type )? ASSIGN expressionList )?;


typeDecl            :   TYPE ( typeSpec | L_PAREN ( ( typeSpec END ) )* ( typeSpec ( END )? )? R_PAREN );
typeSpec            :   aliasDecl | typeDef;
aliasDecl           :   IDENTIFIER ASSIGN type;
typeDef             :   IDENTIFIER type;


varDecl             :   VAR ( varSpec | L_PAREN ( ( varSpec END ) )* ( varSpec ( END )? )? R_PAREN );
varSpec             :   identifierList ( type ( ASSIGN expressionList )? | ASSIGN expressionList );




type                :   typeName | typeLit | L_PAREN type R_PAREN;
typeName            :   IDENTIFIER | qualifiedIdent;
typeLit             :   arrayType | structType | pointerType | functionType |
                        interfaceType | sliceType | mapType | channelType;

qualifiedIdent      :   packageName DOT IDENTIFIER;

arrayType           :   L_BRACKET arrayLength R_BRACKET elementType;
arrayLength         :   expression;
elementType         :   type;

structType          :   STRUCT L_CURLY ( fieldDecl END )* ( fieldDecl ( END )? )? R_CURLY;
fieldDecl           :   (identifierList type | embeddedField) ( tag )?;
embeddedField       :   ( ASTERISK )? typeName;
tag                 :   STRING_LIT;

pointerType         :   ASTERISK baseType;
baseType            :   type;

functionType        :   FUNC signature;
signature           :   parameters ( result )?;
result              :   parameters | type;
parameters          :   L_PAREN ( parameterList ( COMMA )? )? R_PAREN;
parameterList       :   parameterDecl ( COMMA parameterDecl )*;
parameterDecl       :   ( identifierList )? ( ELLIPSIS )? type;

interfaceType       :   INTERFACE L_CURLY ( ( methodSpec | interfaceTypeName ) END )* ( ( methodSpec | interfaceTypeName ) ( END )? )? R_CURLY;
methodSpec          :   methodName signature;
methodName          :   IDENTIFIER;
interfaceTypeName   :   typeName;

sliceType           :   L_BRACKET R_BRACKET elementType;
mapType             :   MAP L_BRACKET keyType R_BRACKET elementType;
keyType             :   type;
channelType         :   ( CHAN | CHAN RECEIVE | RECEIVE CHAN ) elementType;




identifierList      :   IDENTIFIER ( COMMA IDENTIFIER )* ;
expressionList      :   expression ( COMMA expression )* ;


expression          :   unaryExpr | expression binary_op expression;


unaryExpr           :   primaryExpr | unary_op unaryExpr;

binary_op           :   LOGICAL_OR | LOGICAL_AND | rel_op | add_op | mul_op;
rel_op              :   EQUALS | NOT_EQUALS | LESS | LESS_OR_EQUALS | GREATER | GREATER_OR_EQUALS;
add_op              :   PLUS | MINUS | OR | CARET;
mul_op              :   ASTERISK | DIV | MOD | LSHIFT | RSHIFT | AMPERSAND | BIT_CLEAR;

unary_op            :   PLUS | MINUS | EXCLAMATION | CARET | ASTERISK | AMPERSAND | RECEIVE;


primaryExpr         :   operand | conversion | methodExpr | primaryExpr selector
                    |   primaryExpr index | primaryExpr slice | primaryExpr typeAssertion
	                |   primaryExpr arguments;

conversion          :   type L_PAREN expression ( COMMA )? R_PAREN;

methodExpr          :   receiverType DOT methodName;
receiverType        :   type;

selector            :   DOT IDENTIFIER;
index               :   L_BRACKET expression R_BRACKET;
slice               :   L_BRACKET ( expression )? COLON ( expression ?) R_BRACKET |
                        L_BRACKET ( expression )? COLON expression COLON expression R_BRACKET;
typeAssertion       :   DOT L_PAREN type R_PAREN;
arguments           :   L_PAREN ( ( expressionList | type ( COMMA expressionList )? ) ( ELLIPSIS )? ( COMMA )? )? R_PAREN;

operand             :   literal | operandName | L_PAREN expression R_PAREN;
literal             :   basicLit | compositeLit | functionLit;
basicLit            :   INT_LIT | FLOAT_LIT | IMAGINARY_LIT | RUNE_LIT | STRING_LIT;
operandName         :   IDENTIFIER | qualifiedIdent;

compositeLit        :   literalType literalValue;
literalType         :   structType | arrayType | L_BRACKET ELLIPSIS R_BRACKET elementType
                    |   sliceType | mapType | typeName;
literalValue        :   L_CURLY ( elementList ( COMMA )? )? R_CURLY;
elementList         :   keyedElement ( COMMA keyedElement )*;
keyedElement        :   ( key COLON )? element;
key                 :   fieldName | expression | literalValue;
fieldName           :   IDENTIFIER;
element             :   expression | literalValue;

functionLit         :   FUNC signature functionBody;




block               :   L_CURLY statementList R_CURLY;
statementList       :   ( statement END )* (statement (END)?)?;


statement           :   declaration | labeledStmt | simpleStmt
                    |   goStmt | returnStmt | breakStmt | continueStmt
                    |   gotoStmt | fallthroughStmt | block | ifStmt
                    |   switchStmt | selectStmt | forStmt | deferStmt;

simpleStmt          :   emptyStmt | expressionStmt | sendStmt
                    |   incDecStmt | assignment | shortVarDecl;


labeledStmt         :   label COLON statement;
label               :   IDENTIFIER;


goStmt              :   GO expression;


gotoStmt            :   GOTO label;


returnStmt          :   RETURN ( expressionList )?;


breakStmt           :   BREAK ( label )?;


continueStmt        :   CONTINUE ( label )?;


fallthroughStmt     :   FALLTHROUGH;

ifStmt              :   IF ( simpleStmt END )? expression block ( ELSE ( ifStmt | block ) )?;


switchStmt          :   exprSwitchStmt | typeSwitchStmt;

exprSwitchStmt      :   SWITCH ( simpleStmt END )? ( expression )? L_CURLY ( exprCaseClause )* R_CURLY;
exprCaseClause      :   exprSwitchCase COLON statementList;
exprSwitchCase      :   CASE expressionList | DEFAULT;

typeSwitchStmt      :   SWITCH ( simpleStmt END )? typeSwitchGuard L_CURLY ( typeCaseClause )* R_CURLY;
typeSwitchGuard     :   ( IDENTIFIER DECLARE_ASSIGN )? primaryExpr DOT L_PAREN TYPE R_PAREN;
typeCaseClause      :   typeSwitchCase COLON statementList;
typeSwitchCase      :   CASE typeList | DEFAULT;
typeList            :   type ( COMMA type )*;


selectStmt          :   SELECT L_CURLY ( commClause )* R_CURLY;
commClause          :   commCase COLON statementList;
commCase            :   CASE ( sendStmt | recvStmt ) | DEFAULT;
recvStmt            :   ( expressionList ASSIGN | identifierList DECLARE_ASSIGN )? recvExpr;
recvExpr            :   expression;


forStmt             :   FOR ( condition | forClause | rangeClause )? block;
condition           :   expression;

forClause           :   ( initStmt )? END ( condition )? END ( postStmt )?;
initStmt            :   simpleStmt;
postStmt            :   simpleStmt;

rangeClause         :   ( expressionList ASSIGN | identifierList DECLARE_ASSIGN )? RANGE expression;


deferStmt           :   DEFER expression;


emptyStmt           :   ; // empty string


expressionStmt      :   expression;


sendStmt            :   channel RECEIVE expression;
channel             :   expression;


incDecStmt          :   expression ( PLUS_PLUS | MINUS_MINUS );


assignment          :   expressionList assign_op expressionList;
assign_op           :   ( add_op | mul_op )? ASSIGN;


shortVarDecl        :   identifierList DECLARE_ASSIGN expressionList;
