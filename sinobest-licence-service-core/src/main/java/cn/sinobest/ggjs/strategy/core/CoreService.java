package cn.sinobest.ggjs.strategy.core;

import java.io.File;
import java.lang.reflect.Constructor;

import cn.sinobest.ggjs.strategy.domain.PackageInfo;
import cn.sinobest.ggjs.strategy.domain.StrategyInfo;
import org.apache.log4j.Logger;

import cn.sinobest.ggjs.strategy.AbstractStrategy;
import cn.sinobest.ggjs.strategy.core.utils.FileUtil;


public class CoreService {

	private static final Logger LOGGER = Logger.getLogger(CoreService.class);
	
	private PackageInfo packageInfo;
	
	private StrategyInfo strategyInfo;
	
	public CoreService(PackageInfo packageInfo, StrategyInfo strategyInfo) {
		this.packageInfo = packageInfo;
		this.strategyInfo = strategyInfo;
	}
	
	/**
	 * 实例化用户自定义编程Strategy
	 * @return
	 * @throws Exception
	 */
	private AbstractStrategy getStrategyInstance() throws Exception {
		Class<?> strategyClass1 =
				Class.forName(strategyInfo.getStrategyName());
		if (!(strategyClass1.newInstance() instanceof AbstractStrategy)) {
			throw new ClassCastException("error class type");
		}
		Constructor constructor = strategyClass1.getConstructor(new Class[] {PackageInfo.class, StrategyInfo.class});
		AbstractStrategy strategy = (AbstractStrategy) constructor.newInstance(new Object[] {packageInfo, strategyInfo});

		return strategy;
	}
	
	public PackageInfo execute() throws Exception {

		String oldJarPath = packageInfo.getPackagePath() + File.separator + packageInfo.getPackgeName();
		String ImpressTempPath = packageInfo.getTargetPath();
		String CompressDes = packageInfo.getTargetPath() + File.separator + packageInfo.getPackgeName();

		File src = new File(oldJarPath);//jar存放的位置
		File des = new File(ImpressTempPath); //unJarpath


		//解压jar包
		FileUtil.unJar(src, des);
		LOGGER.info(src.getAbsolutePath() + "has finished");

		//执行策略

		AbstractStrategy strategy = this.getStrategyInstance();
		packageInfo = strategy.excute();
		LOGGER.info("strategy has finished");

		//打jar包 这里已经被占用
		String JarName = src.getName();
		String CompressSrc = ImpressTempPath + File.separator;
		FileUtil.compressJarByCommond(CompressSrc, CompressDes);
		LOGGER.info("package new jar has finished--" + CompressDes);

		return packageInfo;
	}
	
}
