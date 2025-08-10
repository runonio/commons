


package io.runon.commons.utils.packages.classes;

import io.runon.commons.exception.IORuntimeException;
import io.runon.commons.utils.FileUtil;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * class search
 * @author macle
 */
@Setter
public class ClassSearch {

	/**
	 * 생성자
	 */
	public ClassSearch(){
	}

	private String [] inPackages = null;
	private Class<?> [] inClasses = null;

	private boolean isJavaJar = false;


	public List<Class<?>> search() {

		List<Class<?>> classes = new ArrayList<>();

		String classPath = System.getProperty("java.class.path");
		String [] pathArray = classPath.split(File.pathSeparator);

		String javaHomePath = System.getProperty("java.home");


		String fileExtension = ".class";

		for(String path : pathArray){
			if(!isJavaJar){
				if(path.startsWith(javaHomePath)){
					continue;
				}
			}

			File file = new File(path);
			if(file.isDirectory()){
				String dirPath = file.getAbsolutePath();
				List<File> files = FileUtil.getFileList(file,fileExtension);
				for(File classFile : files){
					String className = classFile.getAbsolutePath().substring(dirPath.length()+1);
					className = className.replace("/",".").replace("\\",".");
					className = className.substring(0, className.length() - fileExtension.length());
					addClass(classes, className);
				}
			}else{
				try (JarFile jarFile = new JarFile(file)) {
					Enumeration< JarEntry> e = jarFile.entries();
					while (e.hasMoreElements()) {
						JarEntry jarEntry = e.nextElement();
						if (jarEntry.getName().endsWith(fileExtension)) {
							String className = jarEntry.getName();
							className = className.replace("/",".").replace("\\",".");
							addClass(classes, className);
						}
					}
				} catch (IOException e) {
                    throw new IORuntimeException(e);
                }
            }

		}

	    return classes;
	}


	public void addClass(List<Class<?>> classes, String className){


		if(inPackages != null){
			boolean isIn = false;
			for(String in : inPackages){

				if(className.startsWith(in)){
					isIn = true;
					break;
				}

			}
			if(!isIn){
				return;
			}
		}

		try{

			//방법	static 초기화 실행	용도
			//Class.forName(className)	O	클래스 로딩 + static 초기화
			// ClassLoader.loadClass(className)	X	클래스 로딩만, static 초기화는 나중에 필요할 때 실행
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Class<?> c = classLoader.loadClass(className);
//
			if(inClasses != null){
				boolean isIn = false;
				for(Class<?> in: inClasses){

					if(ClassUtils.instanceofType(c, in)){
						isIn = true;
						break;
					}
				}
				if(!isIn){
					return;
				}
			}
			classes.add(c);
		}catch (Exception ignore){}
	}

}