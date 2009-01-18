package prodoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.sun.javadoc.Doc;



public class IndexTemplate extends BasicTemplate{
	private boolean firstClass = true;
	
	IndexTemplate() throws IOException, MissingTagException{
		super("index.htm");
		fileName = "index";
	}
	
	private Map<String,String> fields = new LinkedHashMap<String,String>();
	private Map<String,String> methods = new LinkedHashMap<String,String>();
	
	void reset(){
		super.reset();
		fields = new LinkedHashMap<String,String>();
		methods = new LinkedHashMap<String,String>();
	}
	
	List<TemplateTag> getFileTemplateDevideWords(){
		List<TemplateTag> result = new ArrayList<TemplateTag>();
		result.add(TITLE_TAG);
		result.add(LIBNAME_TAG);
		result.add(DESCRIPTION_TAG);
		result.add(LINKS_TAG);
		return result;
	}
	
	String getCompletedMember(){
		fileTemplate.insertTagContent(TITLE_TAG);
		fileTemplate.insertTagContent(LIBNAME_TAG);
		fileTemplate.insertTagContent(DESCRIPTION_TAG);
		setLinks();
		fileTemplate.insertTagContent(LINKS_TAG);
		return fileTemplate.getTemplateContent();
	}
	
	private void setLinks(){
		TreeSet<String> keys = new TreeSet<String>(fields.keySet());
		for(String key:keys){
			LINKS_TAG.addContent(fields.get(key));
		}
		
		keys = new TreeSet<String>(methods.keySet());
		for(String key:keys){
			LINKS_TAG.addContent(methods.get(key));
		}
		
		fields = new LinkedHashMap<String,String>();
		methods = new LinkedHashMap<String,String>();
	}
	
	void addClass(String className){
		StringBuffer linkBuffer = new StringBuffer();
		if(firstClass){
			firstClass = false;
		}else{
			linkBuffer.append("<BR>");
			setLinks();
		}
		linkBuffer.append(className);
		linkBuffer.append("<BR>");
		linkBuffer.append("<BR>");
		LINKS_TAG.addContent(linkBuffer.toString());
	}
	
	void addMember(Doc doc,String className,String membername){
		StringBuffer linkBuffer = new StringBuffer();
		linkBuffer.append("<a href=\"");
		linkBuffer.append(buildFileName(doc,className)+".htm");
		linkBuffer.append("\">");
		linkBuffer.append(membername);
		linkBuffer.append("</a><br>");
		if(doc.isField()){
			fields.put(doc.name(),linkBuffer.toString());
		}else if(doc.isMethod()){
			methods.put(doc.name(),linkBuffer.toString());
		}else if(doc.isClass()){
			LINKS_TAG.addContent(linkBuffer.toString());
		}
		
	}

}
