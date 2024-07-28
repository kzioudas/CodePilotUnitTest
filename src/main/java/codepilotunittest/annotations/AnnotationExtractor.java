package codepilotunittest.annotations;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import java.util.ArrayList;
import java.util.List;

public class AnnotationExtractor {

    public static List<String> extractMethodAnnotations(MethodDeclaration method) {
        List<String> annotations = new ArrayList<>();
        for (AnnotationExpr annotation : method.getAnnotations()) {
            annotations.add(annotation.getNameAsString());
        }
        return annotations;
    }

    public static List<String> extractClassAnnotations(ClassOrInterfaceDeclaration clazz) {
        List<String> annotations = new ArrayList<>();
        for (AnnotationExpr annotation : clazz.getAnnotations()) {
            annotations.add(annotation.getNameAsString());
        }
        return annotations;
    }

    public static CompilationUnit parse(String sourceCode) {
        return StaticJavaParser.parse(sourceCode);
    }
}
