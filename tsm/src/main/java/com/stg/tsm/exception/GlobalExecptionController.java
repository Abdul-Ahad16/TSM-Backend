package com.stg.tsm.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExecptionController {

	@ExceptionHandler(TsmException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public GlobalExecption handleResourceNotFound(TsmException insuranceException,
			HttpServletRequest request) {

		GlobalExecption error = new GlobalExecption();
		error.setErrorMessage(insuranceException.getMessage());
		error.setRequestedURI(request.getRequestURI());

		return error;
	}
	
	@ExceptionHandler(MobileTsmException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MobileGlobalException handleBadRequest(MobileTsmException mobileTsmException,
			HttpServletRequest request) {

		MobileGlobalException error = new MobileGlobalException();
		error.setMessage(mobileTsmException.getMessage());
		error.setData(mobileTsmException.getData());
		error.setStatus(400);

		return error;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public GlobalExecption handleException(final Exception exception,
			final HttpServletRequest request) {

		GlobalExecption error = new GlobalExecption();
		error.setErrorMessage(exception.getMessage());
		error.setRequestedURI(request.getRequestURI());

		return error;
	}
	

}
