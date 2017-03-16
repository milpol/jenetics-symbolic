package com.jarbytes.jenetics.core.handlers;

import com.jarbytes.jenetics.beans.equation.BiEquationPart;
import com.jarbytes.jenetics.beans.EquationPart;
import com.jarbytes.jenetics.core.EquationSplitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class BasicEquationSplitter implements EquationSplitter
{
    private final int maxParts;
    private final Random random;

    public BasicEquationSplitter(final int maxParts,
                                 final Random random)
    {
        this.maxParts = maxParts;
        this.random = requireNonNull(random);
    }

    @Override
    public List<EquationPart> split(final EquationPart equationPart)
    {
        requireNonNull(equationPart);
        if (equationPart.isTerminal()) {
            return Stream.of(equationPart).collect(Collectors.toList());
        } else {
            int equationDepth = getEquationDepth(equationPart);
            final List<EquationPart> equationParts = new ArrayList<>(maxParts);
            split(equationPart, equationParts, equationDepth);
            return equationParts.isEmpty() ?
                    Stream.of(equationPart).collect(Collectors.toList()) :
                    equationParts;
        }
    }

    private void split(final EquationPart equationPart,
                       final List<EquationPart> equationParts,
                       final int equationDepth)
    {
        if (equationParts.size() < maxParts) {
            if (random.nextInt() % equationDepth == 0) {
                equationParts.add(equationPart);
            } else if (!equationPart.isTerminal()) {
                final BiEquationPart biEquationPart = (BiEquationPart) equationPart;
                split(biEquationPart.getLeft(), equationParts, equationDepth);
                split(biEquationPart.getRight(), equationParts, equationDepth);
            }
        }
    }

    private int getEquationDepth(final EquationPart equationPart)
    {
        if (equationPart.isTerminal()) {
            return 0;
        }
        final BiEquationPart biEquationPart = (BiEquationPart) equationPart;
        int leftDepth = getEquationDepth(biEquationPart.getLeft());
        int rightDepth = getEquationDepth(biEquationPart.getRight());
        return (leftDepth > rightDepth) ? leftDepth + 1 : rightDepth + 1;
    }
}