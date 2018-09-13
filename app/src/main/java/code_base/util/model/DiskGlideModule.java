package code_base.util.model;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

import code_base.AppConstants;

/**
 * 自定义glide缓存策略
 */

public class DiskGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(
                new DiskLruCacheFactory(new DiskLruCacheFactory.CacheDirectoryGetter() {
                    @Override
                    public File getCacheDirectory() {
                        return getMyCacheLocationBlockingIO();
                    }
                },50*1024*1024));//自定义缓存路径和容量大小
    }

    //图片缓存路径
    private File getMyCacheLocationBlockingIO() {
        return new File(AppConstants.Glide_DIR);
    }


    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
