package com.jarbytes.jenetics.beans.equation;

import com.jarbytes.jenetics.beans.EquationPart;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public abstract class BiEquationPart extends IdentifiableEquationPart
{
    final EquationPart left;
    final EquationPart right;
    private final String operator;

    BiEquationPart(final EquationPart left,
                   final EquationPart right,
                   final String operator)
    {
        this.left = requireNonNull(left);
        this.right = requireNonNull(right);
        this.operator = requireNonNull(operator);
    }

    public String getOperator()
    {
        return operator;
    }

    public EquationPart getLeft()
    {
        return left;
    }

    public EquationPart getRight()
    {
        return right;
    }

    public <T extends BiEquationPart> T withLeft(final EquationPart left,
                                                 final Class<T> clazz)
    {
        try {
            return clazz.getConstructor(EquationPart.class, EquationPart.class)
                    .newInstance(left, right);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends BiEquationPart> T withRight(final EquationPart right,
                                                  final Class<T> clazz)
    {
        try {
            return clazz.getConstructor(EquationPart.class, EquationPart.class)
                    .newInstance(left, right);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString()
    {
        return Stream.of("(", left.toString(), operator, right.toString(), ")")
                .collect(Collectors.joining());
    }

    @Override
    public boolean isTerminal()
    {
        return false;
    }
}