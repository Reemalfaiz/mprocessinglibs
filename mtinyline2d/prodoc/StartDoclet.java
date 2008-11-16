package prodoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import com.sun.javadoc.*;

public class StartDoclet extends Doclet{
	
	static File libFolder;
	
	static File docFolder;
	
	static File templateFolder;
	
	static File exampleFolder;
	
	static String libName;
	
	static HashMap<String,String> classLinks = new HashMap<String,String>();

	/**
	 * This function is needed to start the generation of the documentation
	 */
	public static boolean start(RootDoc root){	
		setFolders(root);
		try{
			ClassDoc[] classes = root.specifiedPackages()[0].allClasses();
			for (ClassDoc classDoc : classes){
				if(classDoc.tags("@invisible").length == 0){
					classLinks.put(classDoc.name(),new ClassTemplate().buildFileName(classDoc,classDoc.name()));
				}
			}
			ProcessingDoclet pDoclet = new ProcessingDoclet(root.specifiedPackages()[0]);
		}catch (Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	private static void setFolders(RootDoc root){
		PackageDoc packageDoc = root.specifiedPackages()[0];
		
		Tag[]packageTags = packageDoc.tags("@libname");
		if(packageTags.length > 0){
			libName = packageTags[0].text();
		}else{
			libName = packageDoc.name();
		}
		String libfolder = "";
		
		//get the sourcepath from the sourcepath option setup with javadoc call
		for(String[] options:root.options()){
			if(options[0].equals("-sourcepath")){
				libfolder = options[1];
			}
		}
		libFolder = new File(libfolder);
		
		//gets the templatefolder from the library or takes the standard templates if there is non available
		templateFolder = new File(libfolder,"templates");
		if(!templateFolder.exists()){
			templateFolder = new File(StartDoclet.class.getResource("").getPath()+"/templates");
		}
		
		//get the docfolder and copies the ressource files from the templatefolder 
		docFolder = new File(libfolder,"documentation");
		if (!docFolder.exists()){
			docFolder.mkdir();
				
			try{
				copy(new File(templateFolder,"stylesheet.css"),new File(docFolder,"stylesheet.css"));
				File images = new File(templateFolder,"images");
				if(images.exists()){
					File newImages = new File(docFolder,"images");
					if(!newImages.exists())
					newImages.mkdir();
					
					for(String image:images.list()){
						copy(new File(images,image),new File(newImages,image));
					}
				}
			}catch (IOException e){
				System.out.println("Problem with copying sourcefiles!");
				e.printStackTrace();
			}
		}
		
		//get the example folder with the examples pde files for the documentation
		exampleFolder = new File(libfolder,"examples");
	}
	
	
	private static void copy(File src, File dest) throws IOException{
		copy(new FileInputStream(src), new FileOutputStream(dest));
	}

	private static void copy(InputStream fis, OutputStream fos) throws IOException{
		byte buffer[] = new byte[0xffff];
		int nbytes;

		while ((nbytes = fis.read(buffer)) != -1){
			fos.write(buffer, 0, nbytes);
		}

		if (fis != null)
			fis.close();
		if (fos != null)
			fos.close();
	}
}