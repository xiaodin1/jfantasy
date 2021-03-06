package org.jfantasy.framework.util.validator.validators;

import org.jfantasy.framework.util.ognl.OgnlUtil;
import org.jfantasy.framework.util.validator.Validateable;
import org.jfantasy.framework.util.validator.Validator;
import org.jfantasy.framework.util.validator.entities.DefaultValidateable;
import org.jfantasy.framework.util.validator.entities.FieldValidator;
import org.jfantasy.framework.util.validator.exception.Error;
import org.jfantasy.framework.util.validator.exception.StackValidationException;
import org.jfantasy.framework.util.validator.exception.ValidationException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StackValidator implements Validator {
	private Validateable validateable;
	private Map<String, List<FieldValidator>> fieldValidators = new HashMap<String, List<FieldValidator>>();

	public void validate(Object object) throws ValidationException {
		Map<String, List<String>> errorMsg = new HashMap<String, List<String>>();
		String field;
		Object value;
		for (Map.Entry<String, List<FieldValidator>> entry : this.fieldValidators.entrySet()) {
			field = entry.getKey();
			for (FieldValidator fieldValidator : entry.getValue()) {
				value = OgnlUtil.getInstance().getValue(field, object);
				if (value instanceof List<?>) {
					int i = 0;
					for (Iterator<?> localIterator3 = ((List<?>) value).iterator(); localIterator3.hasNext();) {
						Object v = localIterator3.next();
						DefaultValidateable.validate(this.validateable, fieldValidator, field + "[" + i++ + "]", errorMsg, v);
					}
				} else {
					DefaultValidateable.validate(this.validateable, fieldValidator, field, errorMsg, value);
				}
			}
		}
		if (!errorMsg.isEmpty()) {
			StackValidationException exception = new StackValidationException();
			for (Map.Entry<String, List<String>> entry : errorMsg.entrySet()) {
				String key = entry.getKey();
				for (String v : entry.getValue()) {
					exception.addError(new Error(key, v));
				}
			}
			throw exception;
		}
	}

	public void addFieldValidators(String fieldName, List<FieldValidator> fieldValidators) {
		this.fieldValidators.put(fieldName, fieldValidators);
	}

	public void setValidateble(Validateable validateable) {
		this.validateable = validateable;
	}
}