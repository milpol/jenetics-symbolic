package com.jarbytes.jenetics.core.handlers;

import com.jarbytes.jenetics.beans.equation.BiEquationPart;
import com.jarbytes.jenetics.beans.equation.Constant;
import com.jarbytes.jenetics.beans.EquationPart;
import com.jarbytes.jenetics.beans.equation.Variable;
import com.jarbytes.jenetics.core.EquationSimplifier;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public class BasicEquationSimplifier implements EquationSimplifier
{
    @Override
    public EquationPart simplify(final EquationPart equationPart)
    {
        requireNonNull(equationPart);
        if (equationPart.isTerminal()) {
            return equationPart;
        } else {
            final BiEquationPart biEquationPart = (BiEquationPart) equationPart;
            if (isZeroPart(biEquationPart)) {
                switch (biEquationPart.getOperator()) {
                    case "*":
                    case "/":
                        return new Constant(BigDecimal.ZERO);
                    case "+":
                        return isZero(biEquationPart.getLeft()) ?
                                biEquationPart.getRight() :
                                biEquationPart.getLeft();
                    case "-":
                        return isZero(biEquationPart.getRight()) ?
                                biEquationPart.getLeft() : equationPart;
                    default:
                        return equationPart;
                }
            } else if (isBothConstans(biEquationPart)) {
                return new Constant(biEquationPart.eval());
            } else if (isSameVariablesSubtract(biEquationPart)) {
                return new Constant(BigDecimal.ZERO);
            } else {
                return biEquationPart
                        .withLeft(simplify(biEquationPart.getLeft()), biEquationPart.getClass())
                        .withRight(simplify(biEquationPart.getRight()), biEquationPart.getClass());
            }
        }
    }

    private boolean isSameVariablesSubtract(final BiEquationPart biEquationPart)
    {
        return biEquationPart.getLeft() instanceof Variable &&
                biEquationPart.getRight() instanceof Variable &&
                biEquationPart.getLeft().toString().equals(biEquationPart.getRight().toString());
    }

    private boolean isBothConstans(final BiEquationPart biEquationPart)
    {
        return biEquationPart.getLeft() instanceof Constant &&
                biEquationPart.getRight() instanceof Constant;
    }

    private boolean isZeroPart(final BiEquationPart biEquationPart)
    {
        return isZero(biEquationPart.getLeft()) || isZero(biEquationPart.getRight());
    }

    private boolean isZero(final EquationPart equationPart)
    {
        return equationPart instanceof Constant && equationPart.eval().doubleValue() == 0;
    }
}