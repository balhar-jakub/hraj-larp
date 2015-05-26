package cz.hrajlarp.utils;

import cz.hrajlarp.model.entity.HrajUserEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator{

    /**
     * validates data from user forms.
     * checks if all required entries are filled in.
     */
    @Override
    public void validate(Object userToValidate, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Musíte zadat jméno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "required.lastName", "Musíte zadat pøíjmení.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
                "required.userName", "Musíte zadat uživatelské jméno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.password", "Musíte zadat heslo.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordAgain",
                "required.passwordAgain", "Musíte zadat heslo znovu.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "Musíte zadat e-mailovou adresu.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone",
                "required.phone", "Musíte zadat telefonní èíslo.");

        HrajUserEntity user = (HrajUserEntity)userToValidate;

        if(user.getPassword() != null && !(user.getPassword().equals(user.getPasswordAgain()))){
            errors.rejectValue("password", "notmatch.password",
                    "Heslo neodpovídá kontrolnímu údaji");
        }

        if(user.getGenderForm()==null){
            errors.rejectValue("genderForm", "genderForm.required",
                    "Musíte zadat Muž/Žena");
        } else {
            if (user.getGenderForm().trim().equals("M"))
                user.setGender(new Integer(0));
            else if (user.getGenderForm().trim().equals("F"))
                user.setGender(new Integer(1));
            else errors.rejectValue("genderForm", "genderForm.required",
                        "Musíte zadat Muž/Žena");
        }

    }

    public void validateEditedProfile(Object userToValidate, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Musíte zadat jméno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "required.lastName", "Musíte zadat pøíjmení.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
                "required.userName", "Musíte zadat uživatelské jméno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "Musíte zadat e-mailovou adresu.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone",
                "required.phone", "Musíte zadat telefonní èíslo.");

        HrajUserEntity user = (HrajUserEntity)userToValidate;

        if (user.getPassword()!=null && !user.getPassword().trim().equals("")){
	        if(!user.getPassword().equals(user.getPasswordAgain())){
	            errors.rejectValue("password", "notmatch.password",
	                    "Heslo neodpovídá kontrolnímu údaji");
	        }
        }
        
    }

    @Override
    public boolean supports(Class c) {
        return HrajUserEntity.class.isAssignableFrom(c);
    }

}