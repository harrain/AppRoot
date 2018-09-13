package code_base.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.damon.approot.R;
import code_base.util.view.GlideUtil;
import code_base.util.view.UIConvertUtils;

import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;
import java.util.List;


/**
 * Created by data on 2017/8/23.
 * 我的页面 适配器
 */

public class MeFragmentAdapter extends MultiItemTypeAdapter<MeFragmentAdapter.ItemModel> implements FlexibleDividerDecoration.PaintProvider,FlexibleDividerDecoration.VisibilityProvider,HorizontalDividerItemDecoration.MarginProvider{

    private final String tag = "MeFragmentAdapter";


    public MeFragmentAdapter(Context context, List<ItemModel> items) {
        super(context, items);
        //使用了hongyang的rvadapter框架，实现多类别列表item
        addItemViewDelegate(ItemModel.TEXTONLY_LAYOUT, new ItemViewDelegate<ItemModel>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.me_textonly_layout;
            }

            @Override
            public boolean isForViewType(ItemModel item, int position) {
                return item.itemType == ItemModel.TEXTONLY_LAYOUT;
            }

            @Override
            public void convert(ViewHolder holder, ItemModel itemModel, int position) {
                RelativeLayout relativeLayout = holder.getView(R.id.me_textonly_item_layout);
                TextView title = holder.getView(R.id.me_item_tv_content);
                if (position == 1){
                    ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
                    layoutParams.height = UIConvertUtils.dp2px(60);
                    relativeLayout.setLayoutParams(layoutParams);

                    title.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                }

                if (!TextUtils.isEmpty(itemModel.content[0])) {
                    holder.setText(R.id.me_item_tv_content, itemModel.content[0]);
                }
                ImageView arrowIv = holder.getView(R.id.me_item_arrow_iv);
                if (!TextUtils.isEmpty(itemModel.content[1])) {
                    GlideUtil.showImage(mContext, itemModel.content[1], arrowIv, UIConvertUtils.dp2px(60), UIConvertUtils.dp2px(60));
                }
                if (itemModel.drawableId != 0) {
                    arrowIv.setVisibility(View.VISIBLE);
                    arrowIv.setImageResource(itemModel.drawableId);
                }
            }
        });
        addItemViewDelegate(ItemModel.EMPTY_LAYOUT, new ItemViewDelegate<ItemModel>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.empty_view;
            }

            @Override
            public boolean isForViewType(ItemModel item, int position) {
                return item.itemType == ItemModel.EMPTY_LAYOUT;
            }

            @Override
            public void convert(ViewHolder holder, ItemModel itemModel, int position) {

            }
        });
    }
    //使用了flexibledivider框架实现多形态的分隔线
    @Override
    public Paint dividerPaint(int position, RecyclerView parent) {
        Paint paint = new Paint();
        paint.setColor(mContext.getResources().getColor(R.color.decor_line));
        paint.setStrokeWidth(1);
        return paint;
    }

    @Override
    public boolean shouldHideDivider(int position, RecyclerView parent) {
        if (position == 0 || position == 1 ||  position == 3|| position == 2 || position == 4 || position == 5 || position == 6) return true;
        return false;
    }

    @Override
    public int dividerLeftMargin(int position, RecyclerView parent) {
        int px = UIConvertUtils.dp2px(14);
        return px;
    }

    @Override
    public int dividerRightMargin(int position, RecyclerView parent) {
        int px = (int) mContext.getResources().getDimension(R.dimen.setting_item_paddingstart);//读dimens会自动转成px
        return px;
    }


    public static class ItemModel {
        public static final int TEXTONLY_LAYOUT = 1;
        public static final int EMPTY_LAYOUT = 3;

        public int itemType;
        public String[] content;
        public Intent intent;
        public int drawableId;

        public ItemModel(int itemType, String[] content) {
            this.itemType = itemType;
            this.content = content;
        }

        public ItemModel(int itemType, String[] content, Intent intent) {
            this.itemType = itemType;
            this.content = content;
            this.intent = intent;
        }

        public ItemModel(int itemType, String[] content, Intent intent, int drawableId) {
            this.itemType = itemType;
            this.content = content;
            this.intent = intent;
            this.drawableId = drawableId;
        }

        public Intent getIntent() {
            return intent;
        }

        @Override
        public String toString() {
            return "ItemModel{" +
                    "itemType=" + itemType +
                    ", content=" + Arrays.toString(content) +
                    ", intent=" + intent +
                    ", drawableId=" + drawableId +
                    '}';
        }
    }

}
