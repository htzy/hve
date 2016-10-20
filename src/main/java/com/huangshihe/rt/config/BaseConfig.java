package com.huangshihe.rt.config;

import com.huangshihe.rt.controller.AwlController;
import com.huangshihe.rt.controller.GameController;
import com.huangshihe.rt.controller.MainController;
import com.huangshihe.rt.handler.WebSocketHandler;
import com.huangshihe.rt.model._MappingKit;
import com.huangshihe.rt.util.ErrorRenderFactory;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;

/**
 * API引导式配置
 */
public class BaseConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("a_little_config.txt");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setViewType(ViewType.JSP);
        me.setUrlParaSeparator("-");
//        you can use error render factory to write you own error solution.
//        me.setErrorRenderFactory(new ErrorRenderFactory());
        me.setError401View("/WEB-INF/view/error.jsp");
        me.setError404View("/WEB-INF/view/error.jsp");
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", MainController.class, "/WEB-INF/view/main/");
        me.add("/game", GameController.class, "/WEB-INF/view/game/");
        me.add("/game_awl", AwlController.class, "/WEB-INF/view/game/awl");
    }

    @Override
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        C3p0Plugin C3p0Plugin = createC3p0Plugin();
        me.add(C3p0Plugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(C3p0Plugin);
        me.add(arp);

        // 所有配置在 MappingKit 中搞定
        _MappingKit.mapping(arp);

        me.add(new EhCachePlugin());
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {
        //加入路径，在页面中可以使用basePath，其值为/
        me.add(new ContextPathHandler("basePath"));
        me.add(new WebSocketHandler());
    }

    public static C3p0Plugin createC3p0Plugin() {
        return new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }
}

