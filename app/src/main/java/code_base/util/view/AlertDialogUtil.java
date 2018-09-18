package code_base.util.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

import com.damon.approot.R;
import code_base.util.LogUtils;
import code_base.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by data on 2017/8/18.
 * 提示对话框工具类
 */

public class AlertDialogUtil {
    private static final String tag = "AlertDialogUtil";
    /**
     * 只显示"好"的强制对话框
     */
    public static void showForceAlertDialog(Context context,String title,String message,final AlertListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.positiveResult(dialog,which);
            }
        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
    /**
     * 显示正常的可取消的对话框
     */
    public static void showAlertDialogTwo(Context context,String title,String message,final AlertListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.positiveResult(dialog,which);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static void showAlertDialogTwo(Context context,String title,String message,String positiveButtonText,String negativeButtonText,final AlertListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.positiveResult(dialog,which);
            }
        });
        builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static void showTipDialog(Context context,String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public static void showMultiChoicesDialog(Context context, String title, final String[] itemTexts, final MultiChoicesItemListener listener){
        final ArrayList<Integer> itemWhichSelected = new ArrayList<>();
        final ArrayList<String> itemSelected = new ArrayList<>();
        final boolean[] items = new boolean[itemTexts.length];
        for (boolean item : items) {
            item = false;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMultiChoiceItems(itemTexts, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                LogUtils.i(tag,"OnMultiChoiceClickListener","which "+which);
                items[which] = isChecked;
            }
        });
        builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.i(tag,"OnClickListener","which "+which);
                for (int i = 0; i < items.length; i++) {
                    if (items[i]) {
                        itemWhichSelected.add(i);
                        itemSelected.add(itemTexts[i]);
                    }
                }
                if (itemWhichSelected.size() == 0){
                    ToastUtil.showShortToast("您没有做出选择");
                    return;
                }
                if (listener!=null)
                listener.positiveResult(dialog,which,itemWhichSelected,itemSelected);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        Button posiviveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        posiviveButton.setTextColor(context.getResources().getColor(R.color.colorAccent));
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(context.getResources().getColor(R.color.light_black));

    }

    public static void showSingleChoiceDialog(Context context, String title, final String[] itemTexts, final SingleChoiceItemListener listener){
        final int[] itemWhichSelected = {-1};
        final String[] itemSelected = new String[1];
        final boolean[] items = new boolean[itemTexts.length];
        for (boolean item : items) {
            item = false;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(itemTexts, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.i(tag,"setSingleChoiceItems","which "+which);
                itemWhichSelected[0] = which;
                itemSelected[0] = itemTexts[which];
            }
        });
        builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.i(tag,"OnClickListener","which "+which);

                if (itemWhichSelected[0] == -1){
                    ToastUtil.showShortToast("您没有做出选择");
                    return;
                }
                if (listener!=null)
                    listener.positiveResult(dialog,which,itemWhichSelected[0],itemSelected[0]);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        Button posiviveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        posiviveButton.setTextColor(context.getResources().getColor(R.color.colorAccent));
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(context.getResources().getColor(R.color.light_black));

    }

    public static void showEditTextDialog(Context context, String editText, final EditTextItemListener listener){
        final EditText mEditText = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(mEditText);
        mEditText.setText(editText);
        builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.positiveResult(dialog,which,mEditText.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        Button posiviveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        posiviveButton.setTextColor(context.getResources().getColor(R.color.colorAccent));
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(context.getResources().getColor(R.color.light_black));
    }

    public static void showItemDialog(Context context, String[] items, final AlertListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.i(tag,"setItems","which "+which);
                listener.positiveResult(dialog, which);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        Button posiviveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        posiviveButton.setTextColor(context.getResources().getColor(R.color.colorAccent));
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(context.getResources().getColor(R.color.light_black));
    }

    public interface AlertListener{
        void positiveResult(DialogInterface dialog, int which);
    }

    public interface MultiChoicesItemListener{
        void positiveResult(DialogInterface dialog, int which, ArrayList<Integer> itemsWhichSelected, ArrayList<String> itemsSelected);
    }

    public interface SingleChoiceItemListener{
        void positiveResult(DialogInterface dialog, int which, int itemsWhichSelected, String itemsSelected);
    }

    public interface EditTextItemListener{
        void positiveResult(DialogInterface dialog, int which, String textEdited);
    }
}
