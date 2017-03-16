package com.jarbytes.jenetics.core.handlers;

import com.jarbytes.jenetics.beans.EquationPart;
import com.jarbytes.jenetics.beans.equation.BiEquationPart;
import com.jarbytes.jenetics.core.EquationJoiner;

import java.util.List;

public class BasicEquationJoiner implements EquationJoiner
{
    @Override
    public EquationPart join(final EquationPart equation,
                             final List<EquationPart> originalParts,
                             final List<EquationPart> newParts)
    {
        if (originalParts.contains(equation)) {
            return newParts.get(originalParts.indexOf(equation));
        } else if (!equation.isTerminal()) {
            final BiEquationPart biEquationPart = (BiEquationPart) equation;
            return biEquationPart
                    .withLeft(join(
                            biEquationPart.getLeft(),
                            originalParts,
                            newParts), biEquationPart.getClass())
                    .withRight(join(
                            biEquationPart.getRight(),
                            originalParts,
                            newParts), biEquationPart.getClass());
        } else {
            return equation;
        }
    }
}