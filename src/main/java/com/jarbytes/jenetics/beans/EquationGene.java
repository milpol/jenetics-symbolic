package com.jarbytes.jenetics.beans;

import com.jarbytes.jenetics.core.EquationGenerator;
import com.jarbytes.jenetics.core.EquationValidator;
import org.jenetics.Gene;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class EquationGene implements Gene<EquationPart, EquationGene>
{
    private final EquationPart equation;
    private final EquationGenerator equationGenerator;
    private final EquationValidator equationValidator;

    public EquationGene(final EquationPart equation,
                        final EquationGenerator equationGenerator,
                        final EquationValidator equationValidator)
    {
        this.equation = requireNonNull(equation);
        this.equationGenerator = requireNonNull(equationGenerator);
        this.equationValidator = requireNonNull(equationValidator);
    }

    @Override
    public EquationPart getAllele()
    {
        return equation;
    }

    @Override
    public EquationGene newInstance()
    {
        return new EquationGene(equationGenerator.generate(), equationGenerator, equationValidator);
    }

    @Override
    public EquationGene newInstance(final EquationPart equation)
    {
        return new EquationGene(equation, equationGenerator, equationValidator);
    }

    @Override
    public boolean isValid()
    {
        return equationValidator.isValid(equation);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquationGene that = (EquationGene) o;
        return Objects.equals(equation, that.equation);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(equation);
    }
}