package cn.sinobest.ggjs.strategy.core.utils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.*;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;

import javax.management.RuntimeErrorException;

public class FileUtil {

    public static void copyFile(File src , File des) throws Exception{
        FileChannel input = null;
        FileChannel output = null;
        try {
            input = new FileInputStream(src).getChannel();
            output = new FileOutputStream(des).getChannel();
            output.transferFrom(input,0,input.size());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static String copyJar(File src , File des) throws IOException {
        JarInputStream jarIn = new JarInputStream(new BufferedInputStream(new FileInputStream(src)));
        Manifest manifest = jarIn.getManifest();
        if(!des.getParentFile().exists()) des.getParentFile().mkdirs();

        JarOutputStream jarOut = null;
        if(manifest == null){
            jarOut = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(des)));
        }else{
            jarOut = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(des)),manifest);
        }

        byte[] bytes = new byte[1024 * 20];
        try {
            while(true){
                //重点
                ZipEntry entry = jarIn.getNextJarEntry();
                if(entry == null)
                    break;
                jarOut.putNextEntry(entry);

                int len = jarIn.read(bytes, 0, bytes.length);
                while(len != -1){
                    jarOut.write(bytes, 0, len);
                    len = jarIn.read(bytes, 0, bytes.length);
                }
            }
        } finally {
            jarIn.close();
            jarOut.finish();
            jarOut.close();
        }
        return des.getAbsolutePath();

    }

    public static String unJar(File src,File desDir) throws IOException {
        Long start = System.currentTimeMillis();
        JarFile jarFile = new JarFile(src);
        Enumeration<JarEntry> jarEntrys = jarFile.entries();
        if(!desDir.exists())
            desDir.mkdirs();

        byte[] bytes = new byte[1024 * 20];

        while(jarEntrys.hasMoreElements()){
            ZipEntry entryTemp = jarEntrys.nextElement();
            File desTemp = new File(desDir.getAbsolutePath() + File.separator + entryTemp.getName());

            if(entryTemp.isDirectory()){//目录
                if(!desTemp.exists())
                    desTemp.mkdir();

            }else {//文件
                File desTempParent = desTemp.getParentFile();
                if(!desTempParent.exists())desTempParent.mkdirs();

                BufferedInputStream in = null;
                BufferedOutputStream out = null;
                try {
                    in = new BufferedInputStream(jarFile.getInputStream(entryTemp));
                    out = new BufferedOutputStream(new FileOutputStream(desTemp));

                    int len = in.read(bytes, 0, bytes.length);
                    while(len != -1){
                        out.write(bytes, 0, len);
                        len = in.read(bytes, 0, bytes.length);
                    }
                } finally {//防止失败导致的内存泄露

                    if(in!=null)
                        in.close();
                    if(out!=null){
                        out.flush();
                        out.close();
                    }
                }
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println((end-start)/1000+"s");
        return desDir.getAbsolutePath();
    }

    public static void compressJarByCommond(String CompressSrc,String CompressDes) throws IOException {
        Process process = null;
        try {
            String commond;
            String OS = System.getProperty("os.name").toLowerCase();

            File file = new File(CompressDes);
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();

            if(OS.indexOf("linux") >= 0){
                commond = "jar -cfM0 "+CompressDes+" -C "+CompressSrc+" .";
                process = Runtime.getRuntime().exec(commond);
                process.waitFor();

            }else if(OS.indexOf("windows") >= 0){

                System.out.println("windows");
                String jarcomond = System.getProperty("java.home").substring(0,System.getProperty("java.home").indexOf("jre"))+"bin\\";
                commond = jarcomond+"jar -cfM0 "+CompressDes+" -C "+CompressSrc+" .";

                System.out.println("执行commond"+commond);
                process = Runtime.getRuntime().exec(commond);
            }else
                throw  new RuntimeException("your system is no support");
            int state = 0;
            if((state = process.waitFor()) != 0){
                System.out.println(state);
                throw  new RuntimeException("compressJar no success");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            process.destroy();
        }

    }




    private static void chooseAllFiles(File file , List<File> files){
        if(file.isDirectory()){
            for(File child : file.listFiles())
                chooseAllFiles(child,files);
        }else
            files.add(file);
    }

    public static boolean deleteDir(File someFile) {
        if (!someFile.exists()) {
            System.out.println("[deleteDir]File " + someFile.getAbsolutePath()
                    + " does not exist.");
            return false;
        }
        if (someFile.isDirectory()) {// is a folder
            File[] files = someFile.listFiles();
            for (File subFile : files) {
                boolean isSuccess = deleteDir(subFile);
                if (!isSuccess) {
                    return isSuccess;
                }
            }
        } else {// is a regular file
            boolean isSuccess = someFile.delete();
            if (!isSuccess) {
                return isSuccess;
            }
        }
        if (someFile.isDirectory()) {
            return someFile.delete();
        } else {
            return true;
        }
    }

    public static ArrayList<String> findClassPath(String unJarpath) {
        File file = new File(unJarpath);
        if(!file.exists()){
            throw  new RuntimeException("classpath is empty");
        }
        ArrayList<String> classPaths = new ArrayList<String>();
        classPaths.add(unJarpath);
        findAllClassAndJarPath(classPaths,file);
        return classPaths;
    }

    private static void findAllClassAndJarPath(ArrayList<String> classPaths,File file) {
        if(file.isDirectory()){
            if(file.getName().indexOf("classes") >= 0){
                classPaths.add(file.getAbsolutePath());
            }
            for(File child : file.listFiles())
                findAllClassAndJarPath(classPaths,child);
        }else {
            if(file.getName().indexOf(".jar") >=0){
                classPaths.add(file.getAbsolutePath());
            }
        }
    }
    /*get the path where the running project's jar in
     *     
     */
    public static String getLibPath() {
    	java.net.URL url = FileUtil.class.getProtectionDomain().getCodeSource()
                .getLocation();
        String filePath = null;
        try {
            filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar"))
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        File file = new java.io.File(filePath);
        if(file.exists()) {
        	return file.getAbsolutePath();
        }else {
			throw new RuntimeException("lib no exsit");
		}
        
    }
    
    
    

}
