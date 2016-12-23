# 仿微博添加按钮弹出菜单
![image](https://github.com/joelan/WeiboPopupMenu/raw/master/screenshoot/Screenshot_2016-12-10-20-00-44-686_com.example.ad.png)
![image](https://github.com/joelan/WeiboPopupMenu/raw/master/screenshoot/Screenshot_2016-12-10-20-00-50-299_com.example.ad.png)

##1.依赖
###依赖库分发在Jcenter。
```java

 repositories { 
        jcenter()
   }

dependencies {
compile 'com.lrq.menu:addpopmenu:1.0.2'
}

```
##2.使用
在对应要弹出的按钮事件上加上这段代码
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

###（1）在PopMenu以下属性的set方法都可以自定义相对应的自定义功能（调用.build()后调用,show（）调用之前）
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
    private int mCloseButtomResourceid=R.drawable.tabbar_compose_background_icon_close;

    /**
     * Menu相对于屏幕顶部的距离的的倍数因子（屏幕高度减去菜单本身高度剩下部分除以这个倍数因子）
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
###（2）在PopMenu以下属性的set方法都可以自定义相对应的自定义功能（调用.build()前调用）

    ```java
    /**
     * 一行多少列，默认三列
    *
    */
        public Builder columnCount(int count) {
            this.columnCount = count;
            return this;
        }

    /**
     *添加菜单项
     *
     */
        public Builder addMenuItem(PopMenuItem menuItem) {
            this.itemList.add(menuItem);
            return this;
        }
    /**
     * 动画执行时间，默认300毫秒
    *
    */
        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }
    /**
     * 拉力系数，默认10
    *
    */
        public Builder tension(double tension) {
            this.tension = tension;
            return this;
        }

    /**
     * 摩擦力系数默认5
    *
    */
        public Builder friction(double friction) {
            this.friction = friction;
            return this;
        }
    /**
     * item水平之间的间距
     */
        public Builder horizontalPadding(int padding) {
            this.horizontalPadding = padding;
            return this;
        }
    /**
     * item竖直之间的间距
     */
        public Builder verticalPadding(int padding) {
            this.verticalPadding = padding;
            return this;
        }
    /**
     * item点击监听器
     */
        public Builder setOnItemClickListener(PopMenuItemListener listener) {
            this.popMenuItemListener = listener;
            return this;
        }
    
    ```
    
