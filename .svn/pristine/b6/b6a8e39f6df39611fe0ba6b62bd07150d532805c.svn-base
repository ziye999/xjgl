package com.jiajie.filter;

import java.io.IOException;  
import java.util.Calendar;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse; 
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;

import com.jiajie.util.Constant;
import com.jiajie.util.MemCached;
import com.jiajie.util.SyncDataForCache;
import com.jiajie.util.SysContent;

public class RequestFilter implements Filter {

	private static List<String> urlList;

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	
	public void destroy() {
		SysContent.setRequest(null);
		SysContent.setResponse(null);
		System.out.println("destroy==================");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hsReq = (HttpServletRequest) request;
		HttpServletResponse hsRep = (HttpServletResponse) response; 
		SysContent.setRequest(hsReq);
		SysContent.setResponse(hsRep); 
		if(Constant.ip==null){
			Constant.setIp(hsReq.getLocalAddr());
			Log.error(Constant.ip+"  : "+Constant.isOk());
			System.out.println(Constant.ip+"  : "+Constant.isOk());
			SyncDataForCache.getInstance();
		}
		//判断是否登录
		Cookie[] cooks = hsReq.getCookies();
		if(hsReq.getSession().getAttribute("mBspInfo")==null && cooks!=null){
			for (int i = 0; i < cooks.length; i++) {
				Cookie cook = cooks[i];
				if("KSGLSESSIONID".equals(cook.getName())){
					if(hsReq.getSession().getAttribute("mBspInfo")==null){
						if(cook.getValue()!=null && !"".equals(cook.getValue())){
							hsReq.getSession().setAttribute("mBspInfo", MemCached.getInstance().get(cook.getValue()));
							hsReq.getSession().setAttribute("menutreeList", MemCached.getInstance().get(cook.getValue()+"mt"));
							hsReq.getSession().setAttribute("loginFlag","0");
						}
					}
				}
			}
		}	
		
		String uri = hsReq.getServletPath();
		if(urlList.contains(uri)){
			if(hsReq.getSession().getAttribute("mBspInfo")!=null){ 
				hsReq.getRequestDispatcher("jsp/main.jsp").forward(hsReq, hsRep);
				return;
			}
		} 
		chain.doFilter(hsReq, hsRep);
	}

	public void init(FilterConfig arg0) throws ServletException {
		List<String> list = Constant.djlist; 
		list.add("9ceefdbf5bc24805ae8a1e02851d8873");
		list.add("df94c3d6461a4bfd8242215b721ccf4c");
		list.add("218a14b12e3b469fae215ab0eca4e4ad");
		list.add("76c1e92b033f41899e8fa8d4416742cc");
		list.add("1fe27cdb64e8452bb9bc87bfe191e960");
		list.add("96ecf7c9ef654843bd49754835352d33");
		list.add("ae813818cce14a429204d66270247532");
		list.add("27a975f47c3e4e6da994c5df32050880");
		list.add("7092240b2f9c411c975b7287fdb17498");
		list.add("e4bdd9a622b643679a7b848c3dd608e3");
		list.add("b23a1a2a39dc48f6a3c2d0fdca0d3c71");
		list.add("1c527b387adb4362a4c0f4f9a983646e");
		list.add("e2bd94548497456b82f42b48b19cdfcf");
		list.add("916ff2682ed84f81b60c9a8e06953f08");
		list.add("fa879975fa3748db8e55808d0166db93");
		list.add("8aa5cc4b4a46417ba5099bbd5b40d66c");
		list.add("9072754d33954637989e60ecc81fa4f8");
		list.add("d85f42effc38436c84c9d92f32fb739c");
		list.add("73843d029c7c42d99d735a99fca99d31");
		list.add("5f573c5f83634ef385e6ba08487d8e9d");
		list.add("a6e12128f15b4c20955dd2d4c89bb083");
		list.add("896f1415491d4eb9bbcb352b321c6297");
		list.add("306ab994d7f34501b1eda632b16208b9");
		list.add("7ebd79bb3c5f4413b9310f6a56a9c9c5");
		list.add("81c57f7705644dc2a93097360ceaf234");
		list.add("dd1e10f5a9e8461c8c856278636792e6");
		list.add("26d344d7373f4aa992d4c6d91f30ddad");
		list.add("b9e90d4d17ad459484606cb1a921f9d3");
		list.add("db326496888b41548fc45e689f3595a4");
		list.add("038ddfc0b0584b97bc56cfac9e534704");
		list.add("044e92aae2e449ab83f8a31be8ee625d");
		list.add("3833d0037dbb457dade20e8a1c12030e");
		list.add("a493e12a87bc43b2a63b0b952651a878");
		list.add("4372eb7503954d8ca20e40334a88ade4");
		list.add("38f15d553cdb4b3da5eea2dd9c899dc2");
		list.add("67beeb3e408d4386a36acef667e34696");
		list.add("92aa968188214721be9738bef8aca355");
		list.add("4755b60120f84c6aa0894058cf2daaff");
		list.add("2e913a90e71548aa8a09eab64c346c5f");
		list.add("5f1d2cdb87ad4873b2a080feae1b1a9f");
		list.add("a3142a594a2d4ac2b0415103b8f87e03");
		list.add("99e2f6f192244e6892d7ed8097cf35ae");
		list.add("aa96377b50d84f22ac77e770724e215b");
		list.add("01708fe6c8bd44a4b218e07da59e01f2");
		list.add("b8bcfa9dfdbd421eb56a943464df5ce8");
		list.add("3021149a95964cd1b4ee8c58c1dbc27d");
		list.add("ae5a0fca40c244c3a7feee732e19ae41");
		list.add("d3f5255f12d0419f9cf4c58800095b4b");
		list.add("21d2867767dd4a03b84982e10bf42fb2");
		list.add("86d0f351324b45738808a7496ab89a5a");
		list.add("892a4e4698c649c199c7a890067e1928");
		list.add("3017cfaa8bf8482988a7298a52489878");
		list.add("a244094b5a8c4948a104248b25a5e8f9");
		list.add("bcf13f4f3f6b4369bcccf2f738216959");
		list.add("1032be2d54814be3ae3aa46f74f4a26b");
		list.add("e99dafc6b8a240c0b19155e194f722d0");
		list.add("87499cfb16c04577868fbfe410b70fdf");
		list.add("4759eab4abb84c629d3d09904f61fc02");
		list.add("d7fed2a40cff434899ad8f54e79434a6");
		list.add("32f8c2ae68cf41efa22daf52ddf27cf9");
		list.add("bebc842eed6a47168c744d3532bab8f7");
		list.add("3745b5420fb1444fa1a2e88908e57c7f");
		list.add("315e5f3028f342cbb6a38e047bfa1d2f");
		list.add("7d805849c01d4850ba1d148ef854fed8");
		list.add("3aa59aebb6ca4e638df25eab02e63346");
		list.add("56184204b0324df8b5816c1a68cd6354");
		list.add("8def232fc95e4c69a5a3e0cdf3b854f6");
		list.add("48c74e08ffef4be8b798ff75dfcff152");
		list.add("b5678910dbb94cdcb6a9fba7e29a77d8");
		list.add("04ae4e5d8c304327a4b3baf2bcf1cb2f");
		list.add("9ff4551049ad4a109f73e164bc3d608a");
		list.add("5730318fbc4d49a7bb63fa478de459ab");
		list.add("5da52aeea7ae41e889ec9b00f36be47f");
		list.add("c14757532a11447b89073bc1ea57461a");
		list.add("dd8b631689de4030b7e06a0c288b2237");
		list.add("803ada56956f427092a18e06fb3218c9");
		list.add("b967b18a66c647a8969b033345fa0e6e");
		list.add("315da5428724486ca70a43d1c9ce0a74");
		list.add("00a124af7e8849cb8ac053a060863b08");
		list.add("bda5111e6c1f4f65945682fc32cea45a");
		list.add("4a0146d4c26a408abdb7ac61cb479647");
		list.add("49d4712b722648cda69cc392f6454481");
		list.add("e78e3e3e1f2b4b56829a153926c33122");
		list.add("8fe0bfb5950844ac9659a9ff342f888c");
		list.add("69697ac740c349a88d04bbaa7f871201");
		list.add("bedceb1738e542199aedd2c736809192");
		list.add("1a3a9194290d421aab4425c2169fc78c");
		list.add("bc4e8c501d664d40b55bbcd3ee58cf60");
		list.add("9d3bfddca5c343cea8aeaf44b8153185");
		list.add("99a0c4f14add47408e05b4164d1a1441");
		list.add("709df14fb3aa411eb5263a61b59306ea");
		list.add("e40571f94c1d454c990abbb5fcc20fc4");
		list.add("9b9bbe874f224e73ad893e36c303c602");
		list.add("1e4513f35c9f4bd784770054cc86b38b");
		list.add("7736b834a1bb4fd28389855ecb673734");
		list.add("a639b917e4be44ff9509586a445cbc59");
		list.add("b8670a5f82af4cffbe2b32b58243575f");
		list.add("4eb6ed7d93b949bfb84b1bbf4ae4643d");
		list.add("9b688423e9164b37bbb410f54f28f010");
		list.add("b2ef2d98614e4887b853d8cfde0637ac");
		list.add("312d5a0722e04d2cb07348625be86a61");
		list.add("eb3cfeb2ec05421197c04e468c5347b3");
		list.add("b2c1a2b60341498aba804f1f85d6b508");
		list.add("1623abfcc1504d1a97aebd9c1f0c8475");
		list.add("a1e29282547b4372ba44a04cbf709cc0");
		list.add("4349a200843e48f6bac01060af4bced3");
		list.add("7355e049d0724b258bf06518737999a0");
		list.add("eeb0e18ca0b146e984db511bd580a3fa");
		list.add("1d10a63b812a47178f1fb524f23433e2");
		list.add("cbb21f391ac042918e9de7fcf6a9c710");
		list.add("e3a5e87bd87f4b48b235c98168bb4616");
		list.add("4d96ca60c04d4f71862f4c9f989ba94d");
		list.add("d02c10fa60b44c51979efeabfc92ea6a");
		list.add("336de59ad76d45e7ac846cd25cbde3f5");
		list.add("f6950067c17b44f08a784914715d353b");
		list.add("0fe011ba41004cabb184fec38cf6d097");
		list.add("4360033edc7246619d87ce9eaa13be48");
		list.add("a6d1bac4703b4a7d9cee0a5c9226f9a1");
		list.add("1651ca4656fd45c78d0948e245041755");
		list.add("c78f52fb8896467aa672d246b437bca5");
		list.add("9e53cb38f04644879981cc487de91dd3");
		list.add("563938d993784de8874700a1738baa86");
		list.add("7b61c1520fe147adbc1adaf5d51a4444");
		list.add("6806fd69f6c042e98eacf8098ec36c6d");
		list.add("6c9529be4cac4742be0776d18c041800");
		list.add("359a2242f3f243768bb4141eb38f915f");
		list.add("66ed40af19464d0ab0a8ad439f87019d");
		list.add("5f57f24d46a84a5ab333566901fec8ac");
		list.add("929579fa4a0941a0b109b9d51d2b1639");
		list.add("56aa5f7fe72843609be43aa93b892e75");
		list.add("8910fef36179461699eacba18c1e8b6c");
		list.add("3571d9387efd4916873cb217b63f24de");
		list.add("e823ecdbade749d580640f19f2af4942");
		list.add("931d6e7f9e8041daa9d97c058e63131f");
		list.add("855e8a0b23684b8b86bb2dbbb6e3bd5f");
		list.add("67e830ca98514b90baa1f2ed6d1e9a2f");
		list.add("dfdc772588f3438c9642943c4ac379b8");
		list.add("0b8b53b2f6de45e498498b96df949c5e");
		list.add("5cd6d99bb2e9499ea7da9d3776c0e787");
		list.add("87a66ee0a0ed4f9f985f7a1832aab105");
		list.add("b098ea9eebcc49bfa4c54b5aef5dddf8");
		list.add("d2de961025524c7a852f2ff41ea05ee2");
		list.add("6987861e02984fde9af002daed19c2cc");
		list.add("1dcbe975095945fcb48beb2abcf40c45");
		list.add("050eb2a31c7e482c86372f15c00f9d39");
		list.add("4dae89b398c74884a97a20643834aff3");
		list.add("86a235aacff54a4381f33a466bb74c46");
		list.add("828ada2cf82b40d0ab6b0d8d51d4ee4d");
		list.add("05dfe25346a04dfab727ee456fe2f32e");
		list.add("ec59a2d244904b1f9670c374ac179251");
		list.add("0ee247c4c80e46c080c9c2610e6f42d8");
		list.add("5f4419bdb3894a61b5cca88b46f16f4e");
		list.add("799845e9c04243e4b1a6848d4db0cd6f");
		list.add("466386d95863415fa58e11fc5d6298ab");
		list.add("0e62df09699d4f549631067300fc380f");
		list.add("f6758345b85940f9ac5d346e25a6511f");
		list.add("a6c0f21b89ef474ab89d8267e158aab5");
		list.add("f9bf512dc3134c9caaed394e5b427e17");
		list.add("f72983260aeb4f29a79a7cc26ab38ec1");
		list.add("35cc73fc13044f7a93e2ed4c72703275");
		list.add("36c35e3ed2634d8a82cdee3ec793a49a");
		list.add("7036c0205dbd411393df734a04878664");
		list.add("e996da23289042afaf735e7854795e7d");
		list.add("34a5a3607e254290893463b58fcc61ab");
		list.add("4d086ae3906241ecaa2090bd1e5fa1a6");
		list.add("e655120f9ec04a25873266b37d20bc9f");
		list.add("a098c11dc3f54a02957e5329ef84d7c4");
		list.add("da50949db1224605a7f76e51e8d6a0d3");
		list.add("9ed823d6a91c4961ab903081de207a4a");
		list.add("7497e2bfd9bb421d905dc37bfe06a25e");
		list.add("a0529004696e4d37b27f83ba449db9f7");
		list.add("4559c4db848b4af2840b5ad10c181006");
		list.add("c9006af0b6064fee9a8796375350d083");
		list.add("5812f5f1faa8431a9cb6c93ffb4096dc");
		list.add("7c8e91f61747436698a3fd01a8b97158");
		list.add("68a6d340a6a24c28a1e971b45dd470c2");
		list.add("1c65dbe3f20a4c93870e22a98b59bb57");
		list.add("6c3cdd4071194e8b9427db0139dfd45c");
		list.add("cfd7ad30b9bc49aca36770e5fdcc948e");
		list.add("c398890b2a74488ab9588df835897d00");
		list.add("7d9386173d604042a7ba380de520b982");
		list.add("24ba7d7ce59e4145a74c1fa07bd74064");
		list.add("9c7764060a7841c2997a5de1b9d923cd");
		list.add("f4b2a0ecdac94d6fb146edc34e6715c3");
		list.add("cfc2ab681b10429499951d899b8e8c48");
		list.add("c3ecb050deb94321a82cbc24a7fe3e90");
		list.add("1702b319a8014dc5ab608dc38d3e3923");
		list.add("cbd605ce39384fcc998eb82e107131f6");
		list.add("e1565a4d795743b4bd3bfd0d67d7f5a2");
		list.add("25c16fbba09b46a29971cfe602eb59de");
		list.add("18e1e1769e444e66b49347d57b37729d");
		list.add("806c287a18394d62a20771c5c6a88d2b");
		list.add("60913c6a22f241f7a84722c80e92adfd");
		list.add("74f8227ca5a345939fe4168c2146ebb9");
		list.add("a00843b869064619a0cfe9d984e6f0a6");
		list.add("8ca0ffe821884fe58bd5eacb01073d0d");
		list.add("c842513ac25e4b21a12bc2618dbf6672");
		list.add("05bec6221b33401b96adaf397eb0c85b");
		list.add("0c76ebd707274e4b91afad6e8c237811");
		list.add("d5504f0c6ad94b548556e7cf8711fdae");
	}  
 
}
