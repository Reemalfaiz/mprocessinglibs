package prodoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.javadoc.Doc;
import com.sun.javadoc.Tag;


public class MemberTemplate extends BasicTemplate{
	private final HTML_Template memberreturn;
	
	MemberTemplate() throws IOException, MissingTagException{
		super("member.htm");
		List<TemplateTag> memberreturnDevideWords = new ArrayList<TemplateTag>();
		memberreturnDevideWords.add(MEMBER_RETURN_TAG);
		memberreturn = new HTML_Template("memberreturn.htm",memberreturnDevideWords);
	}
	
	List<TemplateTag> getFileTemplateDevideWords(){
		List<TemplateTag>result = new ArrayList<TemplateTag>();
		result.add(TITLE_TAG);
		result.add(LIBNAME_TAG);
		result.add(RETURN_TAG);
		result.add(USAGE_TAG);
		result.add(RELATED_TAG);
		result.add(NAME_TAG);
		result.add(EXAMPLE_TAG);
		result.add(SYNTAX_TAG);
		result.add(PARAMETERS_TAG);
		result.add(DESCRIPTION_TAG);
		return result;
	}
	
	String getCompletedMember(){
		fileTemplate.insertTagContent(TITLE_TAG);
		fileTemplate.insertTagContent(LIBNAME_TAG);
		fileTemplate.insertTagContent(NAME_TAG);
		fileTemplate.insertTagContent(EXAMPLE_TAG);
		fileTemplate.insertTagContent(DESCRIPTION_TAG);
		fileTemplate.insertTagContent(SYNTAX_TAG);
		fileTemplate.insertTagContent(PARAMETERS_TAG);
		fileTemplate.insertTagContent(RETURN_TAG);
		fileTemplate.insertTagContent(USAGE_TAG);
		fileTemplate.insertTagContent(RELATED_TAG);
		return fileTemplate.getTemplateContent();
	}

	/**
	 * Sets the return section of a doc item
	 * @param doc
	 */
	void setReturnSection(Doc doc){
		if (doc.isMethod()){
			Tag[] returnTag = doc.tags("@return");
			
			if (returnTag.length > 0){
				MEMBER_RETURN_TAG.setContent(returnTag[0].text());
			}else{
				MEMBER_RETURN_TAG.setContent("None");
			}
			memberreturn.insertTagContent(MEMBER_RETURN_TAG);
			RETURN_TAG.setContent(memberreturn.getTemplateContent());
		}
	}
}
