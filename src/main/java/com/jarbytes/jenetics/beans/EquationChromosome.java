package com.jarbytes.jenetics.beans;

import com.jarbytes.jenetics.core.EquationGenerator;
import com.jarbytes.jenetics.core.EquationJoiner;
import com.jarbytes.jenetics.core.EquationSplitter;
import com.jarbytes.jenetics.core.EquationValidator;
import org.jenetics.Chromosome;
import org.jenetics.util.ISeq;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class EquationChromosome implements Chromosome<EquationGene>
{
    private final EquationPart equation;
    private final List<EquationGene> equationGenes;
    private final EquationGenerator equationGenerator;
    private final EquationSplitter equationSplitter;
    private final EquationJoiner equationJoiner;
    private final EquationValidator equationValidator;

    public EquationChromosome(final EquationPart equation,
                              final List<EquationGene> equationGenes,
                              final EquationGenerator equationGenerator,
                              final EquationSplitter equationSplitter,
                              final EquationJoiner equationJoiner,
                              final EquationValidator equationValidator)
    {
        this.equation = requireNonNull(equation);
        this.equationGenes = requireNonNull(equationGenes);
        this.equationGenerator = requireNonNull(equationGenerator);
        this.equationSplitter = requireNonNull(equationSplitter);
        this.equationJoiner = requireNonNull(equationJoiner);
        this.equationValidator = requireNonNull(equationValidator);
    }

    public EquationChromosome(final EquationGenerator equationGenerator,
                              final EquationSplitter equationSplitter,
                              final EquationJoiner equationJoiner,
                              final EquationValidator equationValidator)
    {
        this.equationGenerator = requireNonNull(equationGenerator);
        this.equationSplitter = requireNonNull(equationSplitter);
        this.equationJoiner = requireNonNull(equationJoiner);
        this.equationValidator = requireNonNull(equationValidator);
        this.equation = equationGenerator.generate();
        this.equationGenes = asGenes(equationSplitter.split(equation));
    }

    @Override
    public Chromosome<EquationGene> newInstance(final ISeq<EquationGene> genes)
    {
        final EquationPart newEquation = equationJoiner.join(
                equation,
                asAlleles(equationGenes),
                asAlleles(genes.asList()));
        return new EquationChromosome(
                newEquation,
                asGenes(equationSplitter.split(newEquation)),
                equationGenerator,
                equationSplitter,
                equationJoiner,
                equationValidator);
    }

    private List<EquationPart> asAlleles(final List<EquationGene> equationGenes)
    {
        return equationGenes.stream().map(EquationGene::getAllele).collect(Collectors.toList());
    }

    private List<EquationGene> asGenes(final List<EquationPart> equationParts)
    {
        return equationParts.stream()
                .map(e -> new EquationGene(e, equationGenerator, equationValidator))
                .collect(Collectors.toList());
    }

    @Override
    public EquationGene getGene(int index)
    {
        return equationGenes.get(index);
    }

    @Override
    public int length()
    {
        return equationGenes.size();
    }

    @Override
    public ISeq<EquationGene> toSeq()
    {
        return ISeq.of(equationGenes);
    }

    @Override
    public Iterator<EquationGene> iterator()
    {
        return equationGenes.iterator();
    }

    @Override
    public Chromosome<EquationGene> newInstance()
    {
        final EquationPart equation = equationGenerator.generate();
        return new EquationChromosome(
                equation,
                asGenes(equationSplitter.split(equation)),
                equationGenerator,
                equationSplitter,
                equationJoiner,
                equationValidator);
    }

    @Override
    public boolean isValid()
    {
        return equationValidator.isValid(equation);
    }

    public EquationPart getEquation()
    {
        return equation;
    }
}