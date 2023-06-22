grammar MyLang;
@header {package main.antlr4.ut.pp.parser;}

hellos : HELLO* EOF; 

HELLO : 'Hello' ;

COMMENT : '//' (~('\n'))* -> skip;
WS : [ \n\t\r]+ -> skip;

