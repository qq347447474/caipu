package com.demo.common.config;

import com.demo.index.IndexController;
import com.demo.system.SysConfig;
import com.demo.user.User;
import com.demo.user.UserController;
import com.demo.valicode.ValiCode;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;

/**
 * API引导式配置
 */
public class DemoConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("a_little_config.txt");
        //loadPropertyFile("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		//loadPropertyFile("config.properties");
        //me.setDevMode(getPropertyToBoolean("devMode", false));
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "/index");	// 第三个参数为该Controller的视图存放路径
		me.add("/user", UserController.class);			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/user"
		me.add("/admin", UserController.class);
	}
	
	public static C3p0Plugin createC3p0Plugin() {
		return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		
		// 配置C3p0数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        druidPlugin.setFilters("stat,wall");
        me.add(druidPlugin);
        me.add(new EhCachePlugin());
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setShowSql(PropKit.getBoolean("showSql", false));
		arp.addMapping("user", User.class);
		arp.addMapping("sys_config", SysConfig.class);
		arp.addMapping("valicode", ValiCode.class);
		me.add(arp);
		
		// 所有配置在 MappingKit 中搞定
		//_MappingKit.mapping(arp);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		//me.add(new CommonHandler());
		me.add(new ContextPathHandler("ctx"));
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
