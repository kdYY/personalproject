package cn.sinobest.ggjs.strategy.basic.builder;

import java.io.File;

import cn.sinobest.ggjs.strategy.core.builder.InjectCheckPointBuilder;
import cn.sinobest.ggjs.strategy.builder.AbstractBuilder;
import cn.sinobest.ggjs.strategy.domain.PackageInfo;
import cn.sinobest.ggjs.strategy.domain.StrategyInfo;


public class LicenseCodeBuilder extends AbstractBuilder{
	
	InjectCheckPointBuilder injectCheckPointBuilder;
	
	public LicenseCodeBuilder(PackageInfo packageInfo,StrategyInfo strategyInfo) {
		this.packageInfo = packageInfo;
		this.strategyInfo = strategyInfo;
		InitialInsertContext();
	}
	
	
	public void InitialInsertContext() {
		String JarDecompressTempDir = packageInfo.getTargetPath();
        String[] main = packageInfo.getMainClass().split(":");
        String className = main[0];
        String methodName = main[1];
        String outputPath = JarDecompressTempDir + File.separator + "BOOT-INF" + File.separator + "classes";
        
        String injectCode = "    \ttry {\n" +//固定
                "    \t\tif (!cn.sinobest.ggjs.strategy.StrategyVerify.getInstance().isValid()) {\n" +
                "\tlogger.error(\"***********the license file is invalid (authentication is invalid or license file is expired), the system will exit!!!\");"+
                "        \t\tSystem.exit(-1);\n" +
                "    \t\t}\n" +
                "    \t} catch (Throwable e) {\n" +
                "\tlogger.error(\"***********the license file is invalid (authentication is invalid or license file is expired), the system will exit!!!\");"+
                "    \t\tSystem.exit(-1);\n" +
                "    \t}";
        
		this.injectCheckPointBuilder = new InjectCheckPointBuilder(JarDecompressTempDir, className, methodName, injectCode, outputPath);

	}
	
	@Override
	public PackageInfo build() throws Exception {
		// TODO Auto-generated method stub
		return injectCheckPointBuilder.build();
	}

}
