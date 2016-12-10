# 仿微博添加按钮弹出菜单
##1.依赖
###在需要使用的模块里面的gradle添加下面的依赖。
```java
dependencies {
compile 'com.lrq.menu:addpopmenu:1.0.2'
}

```
##2.使用
```java

    mPopMenu = new PopMenu.Builder().attachToActivity(MainActivity.this)
                .addMenuItem(new PopMenuItem("广场", getResources().getDrawable(R.drawable.tabbar_compose_idea)))
                .addMenuItem(new PopMenuItem("私密", getResources().getDrawable(R.drawable.tabbar_compose_photo)))
                .addMenuItem(new PopMenuItem("家庭圈", getResources().getDrawable(R.drawable.tabbar_compose_headlines)))

                .setOnItemClickListener(new PopMenuItemListener() {
                    @Override
                    public void onItemClick(PopMenu popMenu, int position) {


                        Toast.makeText(MainActivity.this, "你点击了第" + position + "个位置", Toast.LENGTH_SHORT).show();


                    }
                })
                .build();
       mPopMenu.show();         
                
```
##3.自定义

（1）###在PopMenu里以下属性的set方法都可以自定义相对应的自定义功能

```java
   /**

     * 关闭按钮距离屏幕底部位置单位dp
     */
   private int mCloseMenuMarginbottom=15;

    /**
     * 背景颜色
     */
    private int mBackGroundColor=Color.parseColor("#f0f3f3f3");

    /**
     * 关闭按钮的图片
     */


    /**
     * Menu相对于屏幕顶部的距离（去掉菜单本身高度剩下部分除以这个倍数因子）
     */

    private   float mMarginTopRemainSpace=1.5f;

    /**
     * 是否错位弹出菜单
     */
    private   boolean mIsmalpositionAnimatOut=true;

    /**
     * 错位动画时间（毫秒）默认50
     */
    private   int malposition=50;
    
    ```
