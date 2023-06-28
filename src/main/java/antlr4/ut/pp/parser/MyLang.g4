grammar MyLang;
@header {package antlr4.ut.pp.parser;}

module: body;

statement: (definition_statement | compound_statement | expression_statement | iteration_statement | if_statement | return_statement | print_statement | lock_statement | join_statement);
body: statement+;
compound_statement: '{' body '}';

expression_statement: expression? ';';

expression: assignment_expr | fork_expression | get_thread_id_expression;
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

// Maybe we should add break and continue keywords (statements, return break and continue are jump statements)
iteration_statement: while_statement | for_statement;
while_statement : 'while' expression compound_statement;
for_statement: 'for (' ((expression ';') | (var_def)) expression ';' expression ')' compound_statement;

if_statement : 'if'  expression compound_statement elif_part* else_part?;
elif_part: 'elif' expression compound_statement;
else_part: 'else' compound_statement;

fork_expression: FORK compound_statement;
get_thread_id_expression: TID;
join_statement: JOIN expression;

lock_statement: LOCK IDENTIFIER | UNLOCK IDENTIFIER;

definition_statement: var_def | func_def;

func_def: 'fn' IDENTIFIER '(' parameters ')' '{' body '}';
var_def: ((SHARED)? 'var') IDENTIFIER '=' expression ';' ;

return_statement: 'return' expression ';' ;
print_statement: 'print' expression;

primitive_type: INT | BOOL;
compound_type: array | STRING;
array: '[' args ']';

parameters: (parameter (',' parameter)*)?;
parameter: 'var' IDENTIFIER;
args: (expression (',' expression)*)?;

//SHARED: 'shared';
BOOL: 'True' | 'False';
FORK: 'fork';
JOIN: 'join';
TID : 'Tid';
LOCK: 'lock';
UNLOCK: 'unlock';
SHARED: 'shared';

IDENTIFIER: [a-zA-Z_] [a-zA-Z_0-9]*;
INT: '0' | ([1-9] [0-9]*);
STRING: '"' (ESCAPE_SEQUENCE| ~('"'))* '"';
ESCAPE_SEQUENCE: '\\"';

COMMENT : '//' (~('\n'))* -> skip;
WS : [ \n\t\r]+ -> skip;



