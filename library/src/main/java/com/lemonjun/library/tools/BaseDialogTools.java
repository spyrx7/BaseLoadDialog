package com.lemonjun.library.tools;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.lemonjun.library.base.BaseLoadingDialog;
import com.lemonjun.library.base.BaseWarntDialog;
public class BaseDialogTools {

    private static BaseDialogTools app;

    private Dialog baseDialog;

    public static BaseDialogTools getInstance(){
        if(app == null){
            app = new BaseDialogTools();
        }
        return app;
    }

    public BaseLoadingDialog createLoadDialog(Context c){
        if(baseDialog != null){
            baseDialog.dismiss();
        }
        baseDialog = new BaseLoadingDialog(c);
        baseDialog.show();

        return (BaseLoadingDialog)baseDialog;
    }


    public BaseLoadingDialog createLoadDialog(Context c,String text){
        if(baseDialog != null){
            baseDialog.dismiss();
        }

        baseDialog = new BaseLoadingDialog(c);
        baseDialog.show();
        ((BaseLoadingDialog)baseDialog).setLoadText(text);
        return (BaseLoadingDialog)baseDialog;
    }

    public BaseWarntDialog createWarntDialog(Context c){
        if(baseDialog != null){
            baseDialog.dismiss();
        }
        baseDialog = new BaseWarntDialog(c);
        baseDialog.show();
        return (BaseWarntDialog)baseDialog;
    }

}
