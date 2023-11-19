package util;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class BusinessException extends Exception {

    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(final String message) {
        super(message);
        this.message = message;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
    

}
