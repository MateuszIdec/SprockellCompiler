grammar MyLang;
@header {package main.antlr4.ut.pp.parser;}

module: body;

statement: (definition_statement | compound_statement | expression_statement | iteration_statement | if_statement | return_statement | par_statement | lock_statement);
body: statement+;
compound_statement: '{' body '}';

expression_statement: expression? ';';

expression: assignment_expr;
assignment_expr: postfix_expr assignment_operator logical_and_expression | logical_or_expression;
logical_or_expression: logical_and_expression ('||' logical_and_expression)*;
logical_and_expression: relational_expr ('&&' relational_expr)*;
relational_expr: additive_expr (relational_operator additive_expr)*;
additive_expr: multi_expr (additive_operator multi_expr)*; // We can add unary operator and so on with this hierarchy
multi_expr: postfix_expr (multi_operator postfix_expr)*;
postfix_expr: atomic_expr | postfix_expr '[' expression ']' | postfix_expr'(' args ')';
atomic_expr: IDENTIFIER | '(' expression ')' | primitive_type | compound_type;

assignment_operator: '=' | '+=' | '-=' | '*=';
relational_operator: '==' | '!=' | '>=' | '<=' | '>' | '<';
additive_operator: '+' | '-';
multi_operator: '*';

iteration_statement: while_statement | for_statement;
while_statement : 'while' expression body;
for_statement: 'for (' expression ';' expression ';' expression ')' compound_statement;

if_statement : 'if'  expression body elif_part* else_part?;
elif_part: 'elif' expression body;
else_part: 'else' body;


definition_statement: var_def | func_def;

func_def: 'fn' IDENTIFIER '(' parameters ')' '{' body '}';
var_def: (SHARED? VAR) IDENTIFIER '=' expression ';' ;

return_statement: 'return' expression ';' ;

primitive_type: INT | BOOL;
compound_type: array | STRING;
array: '[' args ']';

parameters: (parameter (',' parameter)*);
parameter: VAR IDENTIFIER;
args: (expression (',' expression)*)?;

VAR: 'var';
SHARED: 'shared';
BOOL: 'True' | 'False';
PAR: 'par';
LOCK: 'lock';
UNLOCK: 'unlock';

IDENTIFIER: [a-zA-Z_] [a-zA-Z_0-9]*;
INT: '0' | ([1-9] [0-9]*);
STRING: '"' (ESCAPE_SEQUENCE| ~('"'))* '"';
ESCAPE_SEQUENCE: '\\"';

COMMENT : '//' (~('\n'))* -> skip;
WS : [ \n\t\r]+ -> skip;



