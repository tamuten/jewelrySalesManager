package com.jewelry.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.jewelry.constant.Constant;
import com.jewelry.domain.service.ShozokuService;
import com.jewelry.form.TantoshaForm;

@Component
public class TantoshaFormValidator extends AbstractValidator<TantoshaForm> {

	@Autowired
	private ShozokuService shozokuService;

	@Override
	protected void doValidate(TantoshaForm form, Errors errors) {
		if(form.getShozoku() == null || form.getShozoku().getId() == null) {
			errors.rejectValue("shozoku", "tantoshaForm.shozoku.isNull");
			return;
		}

		final int shozokuId = form.getShozoku().getId();
		final String role = form.getRole();

		if(shozokuService.findByPk(shozokuId) == null) {
			 errors.rejectValue("shozoku", "tantoshaForm.shozoku.isNotExist");
		}

		if(!role.equals(Constant.ROLE_ADMIN) && !role.equals(Constant.ROLE_GENERAL)) {
			errors.rejectValue("role", "tantoshaForm.role.isInvalid");
		}
	}

}
