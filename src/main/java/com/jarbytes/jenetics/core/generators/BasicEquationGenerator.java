package com.jarbytes.jenetics.core.generators;

import com.jarbytes.jenetics.beans.EquationPart;
import com.jarbytes.jenetics.beans.equation.Constant;
import com.jarbytes.jenetics.beans.equation.Divide;
import com.jarbytes.jenetics.beans.equation.Multiply;
import com.jarbytes.jenetics.beans.equation.Subtract;
import com.jarbytes.jenetics.beans.equation.Sum;
import com.jarbytes.jenetics.beans.equation.Variable;
import com.jarbytes.jenetics.core.EquationGenerator;
import com.jarbytes.jenetics.core.EquationValidator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Random;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

public class BasicEquationGenerator implements EquationGenerator
{
    private final EquationValidator equationValidator;
    private final List<String> variables;
    private final List<String> operations;
    private final Random random;
    private final int maxLength;
    private final int terminationChance;
    private final int precision;

    public BasicEquationGenerator(final EquationValidator equationValidator,
                                  final List<String> variables,
                                  final List<String> operations,
                                  final Random random,
                                  final int maxLength,
                                  final int terminationChance,
                                  final int precision)
    {
        this.equationValidator = requireNonNull(equationValidator);
        this.variables = unmodifiableList(variables);
        this.operations = unmodifiableList(operations);
        this.random = requireNonNull(random);
        this.maxLength = maxLength;
        this.terminationChance = terminationChance;
        this.precision = precision;
    }

    @Override
    public EquationPart generate()
    {
        return generate(0);
    }

    private EquationPart generate(int depth)
    {
        if (depth > maxLength || random.nextInt(terminationChance) == 0) {
            return getTerminationPart();
        } else {
            ++depth;
            return getBiEquationPart(operations.get(
                    random.nextInt(operations.size())),
                    generate(depth),
                    generate(depth));
        }
    }

    private EquationPart getBiEquationPart(final String operation,
                                           final EquationPart left,
                                           final EquationPart right)
    {
        switch (operation) {
            case "sum":
                return new Sum(left, right);
            case "subtract":
                return new Subtract(left, right);
            case "divide":
                final Divide divide = new Divide(left, right);
                if (equationValidator.isValid(divide)) {
                    return divide;
                }
            case "multiply":
                return new Multiply(left, right);
            default:
                throw new IllegalArgumentException(String.format("%s is not valid operation", operation));
        }
    }

    private EquationPart getTerminationPart()
    {
        if (random.nextBoolean()) {
            return new Variable(variables.get(random.nextInt(variables.size())));
        } else {
            return new Constant(new BigDecimal(random.nextDouble(), new MathContext(precision)));
        }
    }
}