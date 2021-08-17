package fr.eni.dalDAO;

public class DAOException extends RuntimeException {
	private static final long serialVersionUID = -1545705218812264142L;

		public DAOException( String message ) {
	        super( message );
	    }

	    public DAOException( String message, Throwable cause ) {
	        super( message, cause );
	    }

	    public DAOException( Throwable cause ) {
	        super( cause );
	    }
	}