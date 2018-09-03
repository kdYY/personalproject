import cn.sinobest.ggjs.domain.PackageInfo;
import cn.sinobest.ggjs.domain.StrategyInfo;
import cn.sinobest.ggjs.service.CoreService;

import java.util.HashMap;

/**
 * Created by LY on 2018/9/3.
 */
public class main {
    public static void main(String[] args) {
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.setMainClass("cn.sinobest.sinogear.SinoGearExampleApp:main");
        packageInfo.setPackagePath("C:\\Users\\yaokaidong\\Desktop");
        packageInfo.setPackgeName("sim-manager-0.1.0-SNAPSHOT-exec.jar");
        packageInfo.setPackgeVersion("2.0");
        packageInfo.setTargetPath("C:\\Users\\yaokaidong\\Desktop\\codejar\\sim-managr-0.1.0-SNAPSHOT-exeec");//解压目录+jar名(sim-manager-0.1.0-SNAPSHOT-exec)
        StrategyInfo strategyInfo =new StrategyInfo();
        strategyInfo.setStragegyName("cn.sinobest.ggjs.strategy.StrategyBasic");
        strategyInfo.setCertifiedDays(1000L);
        strategyInfo.setPrivateKey("MIIBSwIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFgIUO8NDui56T9L8vFv0ArwsFtBgjkw=");//key要符合规范
        strategyInfo.setVariables(new HashMap<String,String>());
        CoreService coreService = new CoreService(packageInfo,strategyInfo);
        try {
            packageInfo = coreService.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
