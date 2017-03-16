package com.jarbytes.jenetics.core.fitness;

import com.jarbytes.jenetics.beans.EquationChromosome;
import com.jarbytes.jenetics.beans.EquationGene;
import com.jarbytes.jenetics.beans.EquationPart;
import com.jarbytes.jenetics.core.EquationFitnessFunction;
import org.jenetics.Genotype;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

public class SimpleEquationDispersionFitnessFunction implements EquationFitnessFunction
{
    private final List<String> variables;
    private final List<List<BigDecimal>> values;
    private final List<BigDecimal> results;

    public SimpleEquationDispersionFitnessFunction(final List<String> variables,
                                                   final List<List<BigDecimal>> values,
                                                   final List<BigDecimal> results)
    {
        this.variables = unmodifiableList(variables);
        this.values = unmodifiableList(values);
        this.results = unmodifiableList(results);
    }

    @Override
    public Integer eval(final Genotype<EquationGene> genotype)
    {
        requireNonNull(genotype);
        final EquationPart equation = ((EquationChromosome) genotype.getChromosome()).getEquation();
        final List<BigDecimal> equationResults = values.stream()
                .map(values -> IntStream.range(0, variables.size())
                        .mapToObj(i -> new SimpleEntry<>(variables.get(i), values.get(i)))
                        .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)))
                .map(equation::eval)
                .map(BigDecimal::abs)
                .collect(Collectors.toList());
        return (int) (100 - IntStream.range(0, equationResults.size())
                .mapToObj(i -> equationResults.get(i).subtract(results.get(i)))
                .map(BigDecimal::abs)
                .mapToDouble(BigDecimal::doubleValue)
                .sum());
    }
}