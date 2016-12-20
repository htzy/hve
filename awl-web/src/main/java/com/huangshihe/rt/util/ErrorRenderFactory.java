package com.huangshihe.rt.util;

import com.jfinal.render.ErrorRender;
import com.jfinal.render.IErrorRenderFactory;
import com.jfinal.render.Render;

/**
 * Created by root on 5/20/16.
 */
public class ErrorRenderFactory implements IErrorRenderFactory {
    @Override
    public Render getRender(int errorCode, String view) {
        switch (errorCode){
            case 404:{
                return new ErrorRender(404, "/WEB-INF/view/error.jsp");
            }
            case 500:{

            }break;
            default:
                return new ErrorRender(404, "/WEB-INF/view/error.jsp");
        }
        return new ErrorRender(404, "/WEB-INF/view/error.jsp");
    }
}
