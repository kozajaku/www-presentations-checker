package org.presentation.presentation.validation;


import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * This validator validates password (how suprising!)
 * @author petrof
 */
@Constraint(validatedBy = {ValidPassword.PasswordValidator.class})
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidPassword[] value();
    }
    class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
        @Override
        public void initialize(ValidPassword password) {  }
        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            if (s == null || s.length() < 6) {
                return false;
            }
	    /*
            boolean lower = false, upper = false, digit = false;
            for (char c : s.toCharArray()) {
                lower = lower | Character.isLowerCase(c);
                upper = upper | Character.isUpperCase(c);
                digit = digit | Character.isDigit(c);
            }
            return lower && upper && digit;*/
	    return true;
        }

    }
}