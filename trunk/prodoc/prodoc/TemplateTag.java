package prodoc;

import java.util.LinkedHashSet;


public class TemplateTag implements Comparable<TemplateTag>{
	final boolean required;
	boolean found = false;
	final String name;
	int startposition = 0;
	int endposition = 0;
	int templatePosition = 0;
	private LinkedHashSet<String> content = new LinkedHashSet<String>();
	
	TemplateTag(String name,boolean required){
		this.required = required;
		this.name = name;
	}
	
	public int compareTo(TemplateTag tag){
		return new Integer(startposition).compareTo(tag.startposition);
	}
	
	public void setPositions(int position){
		startposition = position;
		endposition = position + name.length();
	}
	
	String getContent(){
		StringBuffer result = new StringBuffer();
		for(String contentPart:content){
			result.append(contentPart);
		}
		return result.toString();
	}
	
	void setContent(String content){
		this.content = new LinkedHashSet<String>();
		this.content.add(content);
	}
	
	void addContent(String content){
		this.content.add(content);
	}
	
	void resetContent(){
		this.content = new LinkedHashSet<String>();
	}

}
