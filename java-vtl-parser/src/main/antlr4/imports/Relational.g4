grammar Relational;

relationalExpression : unionExpression | joinExpression ;

unionExpression : 'union' '(' datasetExpression (',' datasetExpression )* ')' ;

joinExpression : '[' joinDefinition ']' '{' joinBody '}';

joinDefinition : INNER? joinParam  #joinDefinitionInner
               | OUTER  joinParam  #joinDefinitionOuter
               | CROSS  joinParam  #joinDefinitionCross ;

joinParam : varID (',' varID )* ( 'on' dimensionExpression (',' dimensionExpression )* )? ;

joinBody : joinClause (',' joinClause)* ;

joinClause : role? varID '=' joinCalcExpression # joinCalcClause
           | joinDropExpression                 # joinDropClause
           | joinKeepExpression                 # joinKeepClause
           ;
           //| joinFilter
           //| joinKeep
           //| joinRename ;
           //| joinDrop
           //| joinUnfold
           //| joinFold ;

// Left recursive
joinCalcExpression : leftOperand=joinCalcExpression  sign=( '*' | '/' ) rightOperand=joinCalcExpression #joinCalcProduct
                   | leftOperand=joinCalcExpression  sign=( '+' | '-' ) rightOperand=joinCalcExpression #joinCalcSummation
                   | '(' joinCalcExpression ')'                                                         #joinCalcPrecedence
                   | joinCalcRef                                                                        #joinCalcReference
                   | constant                                                                           #joinCalcAtom
                   ;
// TODO: This is the membership operator!
joinCalcRef : (aliasName=varID '.')? componentName=varID ;


// Drop clause
joinDropExpression : 'drop' joinDropRef (',' joinDropRef)+ ;
joinDropRef : (aliasName=varID '.')? componentName=varID ;

// Keep clause
joinKeepExpression : 'keep' joidKeepRef (',' joidKeepRef)+ ;
joidKeepRef : (aliasName=varID '.')? componentName=varID ;

role : ( 'IDENTIFIER' | 'MEASURE' | 'ATTRIBUTE' ) ;

INNER : 'inner' ;
OUTER : 'outer' ;
CROSS : 'cross' ;

// For tests only
varID               : 'varID' NUM+;
variableRef         : ( 'varName' | 'constant' ) NUM+;
datasetExpression   : 'datasetExpr' NUM*;
dimensionExpression : 'dimensionExpr' NUM*;
constant            : NUM ;

NUM : '0'..'9' ;

WS          : [ \t\n\t] -> skip ;
