package cn.sinobest.ggjs.strategy.core.builder;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.sinobest.ggjs.strategy.builder.AbstractBuilder;
import cn.sinobest.ggjs.strategy.domain.PackageInfo;
import cn.sinobest.ggjs.strategy.core.utils.FileUtil;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class InjectCheckPointBuilder extends AbstractBuilder {

	private static final Logger LOGGER = Logger.getLogger(InjectCheckPointBuilder.class);
	
	private ArrayList<String> classPath = new ArrayList<>();
	private String className;
	private String methodName;
	private String injectCode;
	private String unpackPath;
	private String outputPath;
	
	public InjectCheckPointBuilder(String unpackPath, String className, String methodName, String injectCode, String outputPath ) {
		this.className = className;
		this.methodName = methodName;
		this.injectCode = injectCode;
		this.unpackPath = unpackPath;
		this.outputPath = outputPath;
		
		classPath = FileUtil.findClassPath(unpackPath);
	}

	/**
    *
    * @param classPath
    * @param className 要修改的类路径
    * @param methodName 要修改的方法名
    * @param injectCode   插入的代码
    * @param outputPath    新类输出的路径
    * @throws Exception
    */
   public  void injectCheckCode(ArrayList<String> classPath, String className, String methodName, String injectCode, String outputPath) throws Exception{
       try {
           CtClass class0 = init(classPath, className);
           CtMethod method = class0.getDeclaredMethod(methodName);
           method.insertBefore(injectCode);
           //输出到文件
           class0.writeFile(outputPath);
           //将类进行解冻
           class0.defrost();
           //TODO 回收不了加载的jar
           ClassPool classPool= ClassPool.getDefault();
           classPool.clearImportedPackages();
           //帮助gc
           classPool = null;

       } catch (NotFoundException e1) {
           throw new RuntimeException(e1.getMessage());
       } catch (CannotCompileException e2) {
           throw new RuntimeException(e2.getMessage());
       } catch (IOException e3) {
           throw new RuntimeException(e3.getMessage());
       }
   }
   
	/**
     * 根据classPath构建CtClass
     * @param classPath
     * @param className
     * @return
     */
    public CtClass init(ArrayList<String> classPath, String className)throws Exception{
        try {
            
            ClassPool.getDefault().insertClassPath(classPath.get(0));
            if (classPath.size() > 1) {
                for (int i = 1; i < classPath.size(); i++) {
                    ClassPool.getDefault().appendClassPath(classPath.get(i));
                }
            }
            return ClassPool.getDefault().get(className);
        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
	@Override
	public PackageInfo build() throws Exception {
		injectCheckCode(classPath, className, methodName, injectCode, outputPath);
		// this.packageInfo.appendBuildLog("cn.sinobest.ggjs.builder.InjectCheckPointBuilder bulid()");
		LOGGER.info(className + " has injectCheckPoint");
		return this.packageInfo;
	}
}
