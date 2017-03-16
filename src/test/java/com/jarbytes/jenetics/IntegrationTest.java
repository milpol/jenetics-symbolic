package com.jarbytes.jenetics;

import com.jarbytes.jenetics.beans.EquationChromosome;
import com.jarbytes.jenetics.beans.EquationGene;
import com.jarbytes.jenetics.beans.EquationPart;
import com.jarbytes.jenetics.core.EquationFitnessFunction;
import com.jarbytes.jenetics.core.EquationGenerator;
import com.jarbytes.jenetics.core.fitness.SimpleEquationDispersionFitnessFunction;
import com.jarbytes.jenetics.core.generators.BasicEquationGenerator;
import com.jarbytes.jenetics.core.handlers.BasicEquationJoiner;
import com.jarbytes.jenetics.core.handlers.BasicEquationSimplifier;
import com.jarbytes.jenetics.core.handlers.BasicEquationSplitter;
import com.jarbytes.jenetics.core.validators.DefaultEquationValidator;
import org.jenetics.Genotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.Factory;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntegrationTest
{
    @Test
    public void test()
    {
        final Random random = new Random();
        final List<String> variables = Stream.of("x", "y").collect(Collectors.toList());
        final EquationFitnessFunction simpleEquationFitnessFunction = new SimpleEquationDispersionFitnessFunction(
                variables,
                Stream.of(
                        Stream.of(new BigDecimal(1), new BigDecimal(2)).collect(Collectors.toList()),
                        Stream.of(new BigDecimal(4), new BigDecimal(5)).collect(Collectors.toList())
                ).collect(Collectors.toList()),
                Stream.of(new BigDecimal(10), new BigDecimal(12)).collect(Collectors.toList())
        );
        final DefaultEquationValidator defaultEquationValidator = new DefaultEquationValidator();
        final EquationGenerator dummyEquationGenerator = new BasicEquationGenerator(
                defaultEquationValidator,
                variables,
                Stream.of("sum", "subtract", "multiply", "divide").collect(Collectors.toList()),
                random, 5, 4, 3);
        final BasicEquationSplitter basicEquationSplitter = new BasicEquationSplitter(3, random);
        final BasicEquationJoiner basicEquationJoiner = new BasicEquationJoiner();

        final Factory<Genotype<EquationGene>> genotypeFactory = Genotype.of(
                new EquationChromosome(
                        dummyEquationGenerator,
                        basicEquationSplitter,
                        basicEquationJoiner,
                        defaultEquationValidator));
        final Engine<EquationGene, Integer> engine = Engine
                .builder(simpleEquationFitnessFunction::eval, genotypeFactory)
                .build();

        final Genotype<EquationGene> result = engine
                .stream()
                .limit(20)
                .collect(EvolutionResult.toBestGenotype());

        System.out.println(((EquationChromosome) result.getChromosome()).getEquation().toString());
    }

    @Test
    public void test_simple()
    {
        // 2x+1
        final Random random = new Random();
        final List<String> variables = Stream.of("x").collect(Collectors.toList());
        final EquationFitnessFunction simpleEquationFitnessFunction = new SimpleEquationDispersionFitnessFunction(
                variables,
                Stream.of(
                        Stream.of(new BigDecimal(0)).collect(Collectors.toList()),
                        Stream.of(new BigDecimal(1)).collect(Collectors.toList()),
                        Stream.of(new BigDecimal(2)).collect(Collectors.toList()),
                        Stream.of(new BigDecimal(3)).collect(Collectors.toList())
                ).collect(Collectors.toList()),
                Stream.of(
                        new BigDecimal(1),
                        new BigDecimal(3),
                        new BigDecimal(5),
                        new BigDecimal(7)
                ).collect(Collectors.toList())
        );
        final DefaultEquationValidator defaultEquationValidator = new DefaultEquationValidator();
        final EquationGenerator dummyEquationGenerator = new BasicEquationGenerator(
                defaultEquationValidator, variables, Stream.of("sum", "subtract", "multiply", "divide").collect(Collectors.toList()), random, 5, 4, 3);
        final BasicEquationSplitter basicEquationSplitter = new BasicEquationSplitter(3, random);
        final BasicEquationJoiner basicEquationJoiner = new BasicEquationJoiner();
        final Factory<Genotype<EquationGene>> genotypeFactory = Genotype.of(
                new EquationChromosome(
                        dummyEquationGenerator,
                        basicEquationSplitter,
                        basicEquationJoiner,
                        defaultEquationValidator));
        final Engine<EquationGene, Integer> engine = Engine
                .builder(simpleEquationFitnessFunction::eval, genotypeFactory)
                .build();

        final Genotype<EquationGene> result = engine
                .stream()
                .limit(20)
                .collect(EvolutionResult.toBestGenotype());

        final BasicEquationSimplifier basicEquationSimplifier = new BasicEquationSimplifier();
        final EquationPart best = basicEquationSimplifier.simplify(basicEquationSimplifier.simplify(((EquationChromosome) result.getChromosome()).getEquation()));
        System.out.println(best.toString());
    }
}