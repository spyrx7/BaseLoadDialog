package com.lemonjun.library.tools;

public class BaseDialogTools {

    private static BaseDialogTools app;

    public static BaseDialogTools getInstance(){
        if(app == null){
            app = new BaseDialogTools();
        }
        return app;
    }

}
