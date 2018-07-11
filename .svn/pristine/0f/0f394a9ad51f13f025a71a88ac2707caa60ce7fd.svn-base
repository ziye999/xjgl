(function(){
	Ext.ux.ThemePicker = Ext.extend(Ext.ColorPalette, {
		constructor : function(_cfg) {
			Ext.apply(this, _cfg);
			Ext.ux.ThemePicker.superclass.constructor.call(this, {
				autoShow : true,
				colors : ['000000', '545554', 'ABADAF', 'D8D8D8', '424370', '52567E', '5E7189', 'BDD3EF', 'D1C5FF',
						'9ACD68', '9CD4C1', 'FC6161', 'E2B4D5', 'C49E92', 'FFB5B5', 'FF8C37','C72929'],
				listeners : {
					'select' : function(palette, selColor) {
						switch (selColor) {
							case '000000' : // 黑色（光滑的）
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-slickness.css");
								break;
							case '545554' : // 黑色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-black.css");
								break;
							case 'ABADAF' : // 深灰色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-darkgray.css");
								break;
							case 'D8D8D8' : // 灰色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-gray.css");
								break;
							case '424370' : // 深紫蓝色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-midnight.css");
								break;
							case '52567E' : // 紫蓝色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-indigo.css");
								break;
							case '5E7189' : // 暗蓝灰色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-slate.css");
								break;
							case 'BDD3EF' : // 淡蓝色（默认）
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext-all.css");
								break;
							case 'D1C5FF' : // 紫色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-purple.css");
								break;
							case '9ACD68' : // 橄榄色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-olive.css");
								break;
							case '9CD4C1' : // 绿色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-green.css");
								break;
							case 'FC6161' : // 红色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-red5.css");
								break;
							case 'FFB5B5' : // 薄荷红
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-peppermint.css");
								break;
							case 'E2B4D5' : // 粉红色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-pink.css");
								break;
							case 'C49E92' : // 咖啡色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-chocolate.css");
								break;
							case 'FF8C37' : // 橙色
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"ext/xtheme-orange.css");
								break;
							case 'C72929' : // 红间灰
								Ext.util.CSS.swapStyleSheet("theme", JH.getCssRoot()+"/ext/xtheme-silverCherry.css");
								break;
						}
					}
				}
			});
		}
	});
})();
Ext.reg("uxThemePicker",Ext.ux.ThemePicker);