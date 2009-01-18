package prodoc;

import java.io.IOException;
import java.util.HashMap;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;

import static prodoc.StartDoclet.*;

public class ProcessingDoclet{
	/**
	 * Holds the template for a docmember
	 */
	private final MemberTemplate memberTemplate;
	
	/**
	 * Holds the template for the class doclet
	 */
	private final ClassTemplate classTemplate;
	
	/**
	 * Holds the template for the index file
	 */
	private final IndexTemplate indexTemplate;

	ProcessingDoclet(PackageDoc packageDoc)throws IOException, MissingTagException{

		indexTemplate = new IndexTemplate();
		indexTemplate.setDescription(packageDoc.commentText());
		memberTemplate = new MemberTemplate();
		classTemplate = new ClassTemplate();
		

		ClassDoc[] classes = packageDoc.allClasses();
		for (ClassDoc classDoc : classes){
			if(classDoc.tags("@invisible").length == 0){
				generateMember(classDoc);
			}
		}
		indexTemplate.saveTemplate();
	}
	
	private String oldDocName = "";
	private String docName = "";
	private boolean firstGeneratedDoc = true;
	
	private void generateDoc(Doc doc,String className) throws IOException{
		docName = doc.name();
		if(!oldDocName.equals(docName)){
			if(firstGeneratedDoc){
				firstGeneratedDoc = false;
			}else{
				memberTemplate.saveTemplate();
			}
			memberTemplate.setTitle(libName + " : : " + className + " : : " + docName + " ( )");
			memberTemplate.setName(docName + " ( )");
			memberTemplate.setExample(doc);
			memberTemplate.setDescription(doc.commentText());
			memberTemplate.addSyntax(doc);
			memberTemplate.addParameters(doc);
			memberTemplate.setReturnSection(doc);
			memberTemplate.setUsage(doc);
			memberTemplate.addRelatedmember(doc,fieldLinks,methodLinks);
			memberTemplate.setFileName(doc,className);
		}else{
			memberTemplate.addSyntax(doc);
			memberTemplate.addParameters(doc);
		}
		oldDocName = docName;
	}
	
	private HashMap<String,String> fieldLinks = new HashMap<String,String>();
	private HashMap<String,String> methodLinks = new HashMap<String,String>();
	
	private void insertMemberLinks(ClassDoc classDoc,String className){
		for(FieldDoc fieldDoc:classDoc.fields()){
			if(fieldDoc.isPublic() && fieldDoc.tags("@invisible").length == 0){
				fieldLinks.put(fieldDoc.name(),classTemplate.buildFileName(fieldDoc,className));
			}
		}
		
		for(MethodDoc methodDoc:classDoc.methods()){
			if(methodDoc.isPublic() && methodDoc.tags("@invisible").length == 0){
				methodLinks.put(methodDoc.name()+" ( )",classTemplate.buildFileName(methodDoc,className));
			}
		}
		if(!(classDoc.superclass()== null || classDoc.superclass().name().equals("Object") || classDoc.tags("@nosuperclasses").length > 0)){
			insertMemberLinks(classDoc.superclass(),className);
		}
	}
	
	private void insertConstructors(ClassDoc classDoc,String className) throws IOException{
		for(ConstructorDoc constructorDoc:classDoc.constructors()){
			if(constructorDoc.isPublic() && constructorDoc.tags("@invisible").length == 0){
				classTemplate.addSyntax(constructorDoc);
				classTemplate.addParameters(constructorDoc);
			}
		}
		
		if(!(classDoc.superclass()== null || classDoc.superclass().name().equals("Object") || classDoc.tags("@nosuperclasses").length > 0)){
			insertConstructors(classDoc.superclass(),className);
		}
	}
	
	private void insertFields(ClassDoc classDoc,String className) throws IOException{
		for(FieldDoc fieldDoc:classDoc.fields()){
			if(fieldDoc.isPublic() && fieldDoc.tags("@invisible").length == 0){
				generateDoc(fieldDoc,className);
				classTemplate.addField(fieldDoc);
				indexTemplate.addMember(fieldDoc,className,fieldDoc.name());
			}
		}
		
		if(!(classDoc.superclass()== null || classDoc.superclass().name().equals("Object") || classDoc.tags("@nosuperclasses").length > 0)){
			insertFields(classDoc.superclass(),className);
		}
	}
	
	private void insertMethods(ClassDoc classDoc,String className) throws IOException{
		for(MethodDoc methodDoc:classDoc.methods()){
			if(methodDoc.isPublic() && methodDoc.tags("@invisible").length == 0){
				generateDoc(methodDoc,className);
				classTemplate.addMethod(methodDoc);
				indexTemplate.addMember(methodDoc,className,methodDoc.name()+" ( )");
			}
		}
		
		if(!(classDoc.superclass()== null || classDoc.superclass().name().equals("Object") || classDoc.tags("@nosuperclasses").length > 0)){
			insertMethods(classDoc.superclass(),className);
		}
	}

	void generateMember(ClassDoc classDoc) throws IOException {
		String className = classDoc.name();
		indexTemplate.addClass(className);
		indexTemplate.addMember(classDoc,className,classDoc.name());
		classTemplate.setTitle(libName + " : : " + className);
		classTemplate.setName(className);
		classTemplate.setExample(classDoc);
		classTemplate.setDescription(classDoc.commentText());
		
		insertMemberLinks(classDoc,className);
		insertConstructors(classDoc,className);
		insertFields(classDoc,className);	
		insertMethods(classDoc,className);	
		
		classTemplate.setUsage(classDoc);
		classTemplate.addRelatedmember(classDoc,fieldLinks,methodLinks);
		classTemplate.setFileName(classDoc,className);
		classTemplate.saveTemplate();
	}

}
