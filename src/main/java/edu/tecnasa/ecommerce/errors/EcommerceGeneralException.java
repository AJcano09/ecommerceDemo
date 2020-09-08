package edu.tecnasa.ecommerce.errors;

public class EcommerceGeneralException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EcommerceGeneralException() {
		super();
	}

	public EcommerceGeneralException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EcommerceGeneralException(String arg0) {
		super(arg0);
	}

	public static Throwable getCausaOriginal(Throwable t){
		if(t==null) return null;
		
		int max = 10;
		while(t.getCause()!=null){
			t = t.getCause();
			max--;
			if(max<=0) break;
		}
		
		return t;
	}
}
