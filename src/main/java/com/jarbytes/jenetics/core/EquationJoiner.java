package com.jarbytes.jenetics.core;

import com.jarbytes.jenetics.beans.EquationPart;

import java.util.List;

public interface EquationJoiner
{
    EquationPart join(final EquationPart equation,
                      final List<EquationPart> originalParts,
                      final List<EquationPart> newParts);
}