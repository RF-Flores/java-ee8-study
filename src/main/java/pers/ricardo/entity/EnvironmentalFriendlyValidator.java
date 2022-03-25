package pers.ricardo.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnvironmentalFriendlyValidator implements ConstraintValidator<EnvironmentalFriendly, EngineType> {
    @Override
    public void initialize(EnvironmentalFriendly constraintAnnotation) {
    }

    @Override
    public boolean isValid(EngineType engineType, ConstraintValidatorContext constraintValidatorContext) {
        return engineType.name().equals(EngineType.ELECTRIC.name());
    }
}
