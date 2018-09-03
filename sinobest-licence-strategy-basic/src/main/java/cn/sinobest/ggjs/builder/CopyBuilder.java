package cn.sinobest.ggjs.builder;

import java.io.File;

import javax.swing.text.AbstractDocument.LeafElement;

import org.apache.log4j.pattern.FileLocationPatternConverter;

import cn.sinobest.ggjs.domain.PackageInfo;
import cn.sinobest.ggjs.domain.StrategyInfo;
import cn.sinobest.ggjs.utils.FileUtil;

public class CopyBuilder extends AbstractBuilder {

	public CopyBuilder(PackageInfo packageInfo, StrategyInfo strategyInfo) {
		this.packageInfo = packageInfo;
		this.strategyInfo = strategyInfo;
	}
	
	@Override
	public PackageInfo build() throws Exception {

		String packagePath = packageInfo.getPackagePath();
		String targetPath = packageInfo.getTargetPath();
		String PackgeName = packageInfo.getPackgeName();

		String licFilePath = packagePath + File.separator + "license.lic"; // 缺省在packagePath下
		
		String targetLicFilePath = targetPath +
				File.separator + "BOOT-INF" + File.separator + "classes" + File.separator+ "license.lic";

		// copy .lic
		FileUtil.copyFile(new File(licFilePath), new File(targetLicFilePath));

		String libPath = FileUtil.getLibPath();
		
		//copy log4j to target jar？？？？？
//		FileUtil.copyFile(new File(libPath
//				+ File.separator
//				+ "log4j.jar"), 
//				new File(packageInfo.getTargetPath()
//				+ File.separator
//				+ "BOOT-INF"+File.separator+"lib"
//				+ File.separator
//				+ "sinobest-licence-strategy-basic-verify-0.0.1-SNAPSHOT.jar"));
		
		// set public key
		String verifyJarName = "sinobest-licence-strategy-basic-verify-0.0.1-SNAPSHOT.jar";
		String VerifyJarPath = libPath + File.separator + verifyJarName;
		
		//get the path where the verify jar in
		File file2 = new File(targetPath);
		String targetVerifyJarPath = file2.getParentFile().getAbsolutePath() +File.separator+ verifyJarName.substring(0,verifyJarName.indexOf(".jar"));
		
		//release the target jar
		FileUtil.unJar(new File(VerifyJarPath), new File(targetVerifyJarPath));
		
		//set the license private key
		String VclassToChange = "cn.sinobest.ggjs.strategy.StrategyVerify";
		String VmethodName = "initE";
		String VinsertWord = "try {\n" +
				"            this.e = cn.sinobest.ggjs.util.ByteHex.convert(\""+strategyInfo.getPrivateKey()+"\");\n" +
				"        } catch (Exception var5) {\n" +
				"            var5.printStackTrace();\n" +
				"        }";
		InjectCheckPointBuilder insertVerify = new InjectCheckPointBuilder(targetVerifyJarPath,VclassToChange,VmethodName,VinsertWord,targetVerifyJarPath);
		insertVerify.build();
		
		String CompressSrc = targetVerifyJarPath + "\\";

		//place the verify jar to target jar
		FileUtil.compressJarByCommond(
				CompressSrc,
				packageInfo.getTargetPath()
				+ File.separator
				+ "BOOT-INF"+File.separator+"lib"
				+ File.separator
				+ "sinobest-licence-strategy-basic-verify-0.0.1-SNAPSHOT.jar");


		return packageInfo;
	}

	
}