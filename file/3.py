from Visitor import BaseVisitor
from AST import *

class NameExercise(BaseVisitor):

            
    
    def __init__(self,ast):
        print(ast)
        print()
        self.ast = ast

 
    
    def check(self):
        return self.visit(self.ast, None)

    def visitProgram(self,ast, c): 
        return [VarDecl(x.variable, x.varType) for x in ast.decl]


    