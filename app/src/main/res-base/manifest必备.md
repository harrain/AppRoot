
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" /><!-- 7.0以上APP内安装必备 -->

    <application>

        <meta-data
                android:name="code_base.util.model.DiskGlideModule"
                android:value="GlideModule" />  <!-- 设定全局Glide的图片缓存路径和缓存容量 -->

        <meta-data
                android:name="android.max_aspect"
                android:value="2.5" />          <!-- 匹配全面屏的屏幕 -->

        <receiver android:name="code_base.module.downloadmanager.receiver.InstallReceiver">
                    <intent-filter android:priority="20">
                        <action android:name="android.intent.action.DOWNLOAD_COMPLETE" />
                    </intent-filter>
                </receiver>

        <provider
                    android:name="android.support.v4.content.FileProvider"
                    android:authorities="app.content.fileprovider"
                    android:exported="false"
                    android:grantUriPermissions="true">
                    <meta-data
                        android:name="android.support.FILE_PROVIDER_PATHS"
                        android:resource="@xml/file_paths" />
                </provider>

        <receiver android:name="code_base.ui.receiver.NetStateReceiver">
                    <intent-filter>
                        <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />

                        <category android:name="android.intent.category.DEFAULT" />
                    </intent-filter>
                </receiver>     <!-- 6可用 7以上不可用-->

        <receiver
                    android:name="code_base.ui.receiver.LocaleReceiver"
                    android:enabled="true"
                    android:exported="false">
                    <intent-filter android:priority="1000">
                        <action android:name="android.intent.action.LOCALE_CHANGED"/>
                    </intent-filter>
                </receiver>

    </application>