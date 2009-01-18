package prodoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;


public class ClassTemplate extends BasicTemplate{
	private final HTML_Template classFields;
	private final HTML_Template classField;
	private final HTML_Template classMethods;
	private final HTML_Template classMethod;

	private Map<String,String> fields = new LinkedHashMap<String,String>();
	private Map<String,String> methods = new LinkedHashMap<String,String>();
	
	ClassTemplate() throws IOException, MissingTagException{
		super("class.htm");
		List<TemplateTag> classFieldsDevideWords = new ArrayList<TemplateTag>();
		classFieldsDevideWords.add(FIELD_KIND_TAG);
		classFieldsDevideWords.add(FIELD_TAG);
		classFields = new HTML_Template("classmembers.htm",classFieldsDevideWords);
		
		List<TemplateTag> classFieldDevideWords = new ArrayList<TemplateTag>();
		classFieldDevideWords.add(FIELD_LINK_TAG);
		classFieldDevideWords.add(FIELD_NAME_TAG);
		classFieldDevideWords.add(FIELD_DESCRIPTION_TAG);
		classField = new HTML_Template("classmember.htm",classFieldDevideWords);
		
		List<TemplateTag> classMethodsDevideWords = new ArrayList<TemplateTag>();
		classMethodsDevideWords.add(METHOD_KIND_TAG);
		classMethodsDevideWords.add(METHOD_TAG);
		classMethods = new HTML_Template("classmembers.htm",classMethodsDevideWords);
		
		List<TemplateTag> classMethodDevideWords = new ArrayList<TemplateTag>();
		classMethodDevideWords.add(METHOD_LINK_TAG);
		classMethodDevideWords.add(METHOD_NAME_TAG);
		classMethodDevideWords.add(METHOD_DESCRIPTION_TAG);
		classMethod = new HTML_Template("classmember.htm",classMethodDevideWords);
	}
	
	void reset(){
		super.reset();
		fields = new LinkedHashMap<String,String>();
		methods = new LinkedHashMap<String,String>();
	}
	
	List<TemplateTag> getFileTemplateDevideWords(){
		List<TemplateTag> result = new ArrayList<TemplateTag>();
		result.add(TITLE_TAG);
		result.add(LIBNAME_TAG);
		result.add(NAME_TAG);
		result.add(EXAMPLE_TAG);
		result.add(DESCRIPTION_TAG);
		result.add(FIELDS_TAG);
		result.add(METHODS_TAG);
		result.add(SYNTAX_TAG);
		result.add(PARAMETERS_TAG);
		result.add(USAGE_TAG);
		result.add(RELATED_TAG);
		return result;
	}
	
	String getCompletedMember(){
		fileTemplate.insertTagContent(TITLE_TAG);
		fileTemplate.insertTagContent(LIBNAME_TAG);
		fileTemplate.insertTagContent(NAME_TAG);
		fileTemplate.insertTagContent(EXAMPLE_TAG);
		fileTemplate.insertTagContent(DESCRIPTION_TAG);
		fileTemplate.insertTagContent(getFieldSection());
		fileTemplate.insertTagContent(getMethodSection());
		fileTemplate.insertTagContent(SYNTAX_TAG);
		fileTemplate.insertTagContent(PARAMETERS_TAG);
		fileTemplate.insertTagContent(USAGE_TAG);
		fileTemplate.insertTagContent(RELATED_TAG);
		return fileTemplate.getTemplateContent();
	}
	
	private TemplateTag getFieldSection(){
		TreeSet<String> keys = new TreeSet<String>(fields.keySet());
		for(String key:keys){
			FIELD_TAG.addContent(fields.get(key));
		}
		if(FIELD_TAG.getContent().length()>0){
			FIELD_KIND_TAG.setContent("Fields");
			classFields.insertTagContent(FIELD_KIND_TAG);
			classFields.insertTagContent(FIELD_TAG);
			FIELDS_TAG.setContent(classFields.getTemplateContent());
		}
		return FIELDS_TAG;
	}
	
	private TemplateTag getMethodSection(){
		TreeSet<String> keys = new TreeSet<String>(methods.keySet());
		for(String key:keys){
			METHOD_TAG.addContent(methods.get(key));
		}
		if(METHOD_TAG.getContent().length()>0){
			METHOD_KIND_TAG.setContent("Methods");
			classMethods.insertTagContent(METHOD_KIND_TAG);
			classMethods.insertTagContent(METHOD_TAG);
			METHODS_TAG.setContent(classMethods.getTemplateContent());
		}
		return METHODS_TAG;
	}
	
	void addField(FieldDoc doc){
		FIELD_LINK_TAG.setContent(buildFileName(doc,NAME_TAG.getContent())+".htm");
		classField.insertTagContent(FIELD_LINK_TAG);
		FIELD_NAME_TAG.setContent(doc.name());
		classField.insertTagContent(FIELD_NAME_TAG);
		Tag[] shortdesc = doc.tags("@shortdesc");
		if(shortdesc.length > 0){
			FIELD_DESCRIPTION_TAG.setContent(shortdesc[0].text());
		}else{
			FIELD_DESCRIPTION_TAG.setContent(doc.commentText());
		}
		FIELD_DESCRIPTION_TAG.addContent("<BR><BR>");
		classField.insertTagContent(FIELD_DESCRIPTION_TAG);		
		if(!doc.commentText().equals("")){
			fields.put(doc.name(),classField.getTemplateContent());
		}
	}
	
	void addMethod(MethodDoc doc){
		METHOD_LINK_TAG.setContent(buildFileName(doc,NAME_TAG.getContent())+".htm");
		classMethod.insertTagContent(METHOD_LINK_TAG);
		METHOD_NAME_TAG.setContent(doc.name()+" ( )");
		classMethod.insertTagContent(METHOD_NAME_TAG);
		Tag[] shortdesc = doc.tags("@shortdesc");
		if(shortdesc.length > 0){
			METHOD_DESCRIPTION_TAG.setContent(shortdesc[0].text());
		}else{
			METHOD_DESCRIPTION_TAG.setContent(doc.commentText());
		}
		METHOD_DESCRIPTION_TAG.addContent("<BR><BR>");
		classMethod.insertTagContent(METHOD_DESCRIPTION_TAG);	
		if(!doc.commentText().equals("")){
			methods.put(doc.name(),classMethod.getTemplateContent());
		}
	}
}
