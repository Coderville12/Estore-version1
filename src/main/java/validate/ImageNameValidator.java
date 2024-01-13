package validate;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String>{

	
	private Logger logger= LoggerFactory.getLogger(ImageNameValidator.class);

	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
	
		
	
	//	boolean isValid =(value.isBlank()) ?false:true;
		boolean isValid =!value.isBlank();
		logger.info("Message from isValid : {} and its--> {}", value, isValid);
		return isValid ;
	}

	
	
	
	
}
