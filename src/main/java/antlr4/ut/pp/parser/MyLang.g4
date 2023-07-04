grammar MyLang;
@header {package antlr4.ut.pp.parser;}

module: body;

statement:  var_def
          | compound_statement
          | expression_statement
          | iteration_statement
          | if_statement
          | print_statement
          | lock_statement
          | join_statement;

body: statement+;
compound_statement: '{' body '}';

expression_statement: expression? ';';

expression: (IDENTIFIER assignment_operator (atomic_expr | postfix_expr | logical_or_expression))
            | logical_or_expression;
logical_or_expression: logical_and_expression ('||' logical_and_expression)*;
logical_and_expression: relational_expr ('&&' relational_expr)*;
relational_expr: additive_expr (relational_operator additive_expr)*;
additive_expr: multi_expr (additive_operator multi_expr)*; // We can add unary operator and so on with this hierarchy
multi_expr: postfix_expr (multi_operator postfix_expr)*;
postfix_expr: var_call '[' (primitive_type | expression) ']'
            | atomic_expr;
atomic_expr:  primitive_type
            | var_call
            | get_thread_id_expression
            | read_expression
            | '(' expression ')'
            | compound_type
            | fork_expression;
var_call: IDENTIFIER;

assignment_operator: '=';
relational_operator: '==' | '!=' | '>=' | '<=' | '>' | '<';
additive_operator: '+' | '-';
multi_operator: '*';

iteration_statement: while_statement;
while_statement : 'while' expression compound_statement;

if_statement : 'if'  expression compound_statement elif_part* else_part?;
elif_part: 'elif' expression compound_statement;
else_part: 'else' compound_statement;

fork_expression: FORK compound_statement;
get_thread_id_expression: TID;
join_statement: JOIN (var_call | expression);

lock_statement: LOCK IDENTIFIER
              | UNLOCK IDENTIFIER;

var_def: ((SHARED)? 'var') IDENTIFIER '=' (atomic_expr | postfix_expr | expression) ';' ;

print_statement: 'print' (atomic_expr | expression) ';';
read_expression: 'read';

primitive_type: INT | BOOL;
compound_type: array | STRING;
array: '[' ((atomic_expr | expression) (',' (atomic_expr | expression))*)? ']';


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