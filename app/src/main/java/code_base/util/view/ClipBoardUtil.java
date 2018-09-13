package code_base.util.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import code_base.util.Utils;

/**
 * Created by net on 2018/1/22.
 * 剪贴板工具类
 */

public class ClipBoardUtil {

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public static String getText(){
        ClipboardManager clipboard = (ClipboardManager) Utils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(Utils.getContext()).toString();
        }
        return null;
    }
}
