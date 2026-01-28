package com.gammadevs;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JaninoTest {

    @Test
    public void testOneVariable() throws CompileException, InvocationTargetException {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        evaluator.setExpressionType(double.class);
        evaluator.setParameters(new String[] {"s"}, new Class[] {double.class});
        evaluator.cook("Math.min(0.7, Math.max(0.05, s / 2))");
        assertEquals(0.05, (double) evaluator.evaluate(new Object[] {0.01}), 1e-7);
        assertEquals(0.05, (double) (evaluator.evaluate(new Object[] {0.05})), 1e-7);
        assertEquals(0.05, (double) (evaluator.evaluate(new Object[] {0.10})), 1e-7);
        assertEquals(0.15, (double) (evaluator.evaluate(new Object[] {0.30})), 1e-7);
        assertEquals(0.65, (double) (evaluator.evaluate(new Object[] {1.3})), 1e-7);
        assertEquals(0.7, (double) (evaluator.evaluate(new Object[] {1.5})), 1e-7);
    }

    @Test
    public void testTwoVariables() throws CompileException, InvocationTargetException {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        evaluator.setExpressionType(double.class);
        evaluator.setParameters(new String[] {"s", "v"}, new Class[] {double.class, double.class});
        evaluator.cook("v * Math.min(0.7, Math.max(0.05, s / 2))");
        assertEquals(0.45, (double) (evaluator.evaluate(new Object[] {0.30, 3})), 1e-7);
    }

    @Test
    public void testTwoVariablesSwitchVarOrder() throws CompileException, InvocationTargetException {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        evaluator.setExpressionType(double.class);
        evaluator.setParameters(new String[] {"s", "v"}, new Class[] {double.class, double.class});
        evaluator.cook("Math.min(0.7, Math.max(0.05, s / 2)) * v");
        assertEquals(0.6, (double) (evaluator.evaluate(new Object[] {0.30, 4})), 1e-7);
    }

    @Test
    public void testStepWise() throws CompileException, InvocationTargetException {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        evaluator.setExpressionType(double.class);
        evaluator.setParameters(new String[] {"s"}, new Class[] {double.class});
        evaluator.cook("(s >= 0 && s < 0.2 ? 1 : 0) * 0.1 + (s >= 0.2 && s < 0.4 ? 1 : 0) * 0.15 + (s >= 0.4 ? 1 : 0) * 0.25");
        assertEquals(0.1, (double) (evaluator.evaluate(new Object[] {0.19})), 1e-7);
        assertEquals(0.15, (double) (evaluator.evaluate(new Object[] {0.35})), 1e-7);
        assertEquals(0.25, (double) (evaluator.evaluate(new Object[] {0.405})), 1e-7);
    }

    @Test
    public void testStepWiseWithCustomFunction() throws CompileException, InvocationTargetException {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        evaluator.setExpressionType(double.class);
        evaluator.setParameters(new String[] {"s"}, new Class[] {double.class});
        evaluator.setDefaultImports(new String[] {"static com.gammadevs.ExpressionUtils.in"});
        evaluator.cook("in(s, 0, 0.2) * 0.1 + in(s, 0.2, 0.4) * 0.15 + in(s, 0.4, 1000) * 0.25");
        assertEquals(0.1, (double) (evaluator.evaluate(new Object[] {0.19})), 1e-7);
        assertEquals(0.15, (double) (evaluator.evaluate(new Object[] {0.35})), 1e-7);
        assertEquals(0.25, (double) (evaluator.evaluate(new Object[] {0.405})), 1e-7);
    }

}
