### 概述

> 该百度地图SDK API封装库是为了更方便快速的集成调用地图功能,这里的封装只为提供一种思路。目前市面上大多数APP使用到地图的基本上就是显示地图、显示定位、显示点标记marker和信息窗体。

***本库是建立在熟知百度地图开发文档的基础之上的，否则出错你会不知道怎么解决！***

### 使用说明

- 将该库的文件夹复制粘贴到你的工程目录下，然后进行以下步骤

    - 在settings.gradle 添加 ':bmaplib' 用","与其他module隔开 (可观察本工程下的settings.gradle为例)

    - 在app的build.gradle的dependencies标签下 添加 api project(':bmaplib') 正式依赖该库  (可观察本工程下的app build.gradle为例)

- 申请地图ak,将ak添加到本库的AndroidManifest下的meta-data

> attention: 是本library的AndroidManifest,不是你的APP应用的AndroidManifest

- 在Application下初始化SDK

 `BaiduMapSDK.init(this);`

- 参照PlaceSelectActivity、MarkerInfoWindowActivity、LocationInfoActivity例子使用封装库

> 所以使用到地图的地方皆使用MapFragment，不使用MapView,方便直接在代码里增加地图，不局限于某个Activity某个layout

```
MapFragmentHelper为重点，通过它实现在现有地图上显示定位点、点marker、信息窗、地图相关设置。

构造方法有三种类型：

MapFragmentHelper(FragmentActivity activity,@IdRes int id) 是通过findFragmentById绑定XML里的fragment ；

MapFragmentHelper(@IdRes int containerViewId, FragmentActivity activity) 是通过FragmentManager动态添加MapFragment ；

MapFragmentHelper(MapFragment mapFragment) 是接收一个已经初始化的MapFragment对象，适用于主页viewpager或bottomnavigation切换的场景。
```

