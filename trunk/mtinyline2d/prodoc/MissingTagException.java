package prodoc;


public class MissingTagException extends Exception{
	static final long serialVersionUID = 0;
	
	MissingTagException(TemplateTag tag, String templateFile){
		super("The required Tag: "+tag.name+" could not be found in the template: " + templateFile);
	}
}
