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
                "required.name", "Mus�te zadat jm�no.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "required.lastName", "Mus�te zadat p��jmen�.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
                "required.userName", "Mus�te zadat u�ivatelsk� jm�no.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.password", "Mus�te zadat heslo.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordAgain",
                "required.passwordAgain", "Mus�te zadat heslo znovu.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "Mus�te zadat e-mailovou adresu.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone",
                "required.phone", "Mus�te zadat telefonn� ��slo.");

        HrajUserEntity user = (HrajUserEntity)userToValidate;

        if(user.getPassword() != null && !(user.getPassword().equals(user.getPasswordAgain()))){
            errors.rejectValue("password", "notmatch.password",
                    "Heslo neodpov�d� kontroln�mu �daji");
        }

        if(user.getGenderForm()==null){
            errors.rejectValue("genderForm", "genderForm.required",
                    "Mus�te zadat Mu�/�ena");
        } else {
            if (user.getGenderForm().trim().equals("M"))
                user.setGender(new Integer(0));
            else if (user.getGenderForm().trim().equals("F"))
                user.setGender(new Integer(1));
            else errors.rejectValue("genderForm", "genderForm.required",
                        "Mus�te zadat Mu�/�ena");
        }

    }

    public void validateEditedProfile(Object userToValidate, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                "required.name", "Mus�te zadat jm�no.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "required.lastName", "Mus�te zadat p��jmen�.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
                "required.userName", "Mus�te zadat u�ivatelsk� jm�no.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "Mus�te zadat e-mailovou adresu.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone",
                "required.phone", "Mus�te zadat telefonn� ��slo.");

        HrajUserEntity user = (HrajUserEntity)userToValidate;

        if (user.getPassword()!=null && !user.getPassword().trim().equals("")){
	        if(!user.getPassword().equals(user.getPasswordAgain())){
	            errors.rejectValue("password", "notmatch.password",
	                    "Heslo neodpov�d� kontroln�mu �daji");
	        }
        }
        
    }

    @Override
    public boolean supports(Class c) {
        return HrajUserEntity.class.isAssignableFrom(c);
    }

}