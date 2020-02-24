package com.lemonjun.library.tools;

import android.content.Context;

import com.lemonjun.library.base.BaseLoadingDialog;
import com.lemonjun.library.base.BaseWarntDialog;

public class BaseDialogTools {

    private static BaseDialogTools app;

    public static BaseDialogTools getInstance(){
        if(app == null){
            app = new BaseDialogTools();
        }
        return app;
    }


    public BaseLoadingDialog createLoadDialog(Context c,String text){
        BaseLoadingDialog dialog = new BaseLoadingDialog(c);
        dialog.setLoadText(text);

        return dialog;
    }

    public BaseWarntDialog createWarntDialog(Context c){
        BaseWarntDialog dialog = new BaseWarntDialog(c);
        
        return dialog;
    }


}
