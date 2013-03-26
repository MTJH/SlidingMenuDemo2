SlidingMenuDemo2
================

Android滑动菜单SlidingMenu开源项目使用简单的例子


该工程要运行起来，还需要几步：

1~Android SDK Manager SDK安装Google APIs(如果没安装)，设置Build target: Google APIs(5.2.2)

2~添加依赖lib，Github上开源的(ActionBarSherlock/SlidingMenu)：

//https://github.com/JakeWharton/ActionBarSherlock
git clone https://github.com/JakeWharton/ActionBarSherlock.git

//https://github.com/jfeinstein10/SlidingMenu
git clone https://github.com/jfeinstein10/SlidingMenu.git

其实这两个工程都有example，只依赖library就可以了
import ActionBarSherlock.library/SlidingMenu.library into your workspace.
add ActionBarSherlock as a dependency to SlidingMenu
add SlidingMenu/ActionBarSherlock as a dependency to project

3~编译会出现："The method getSupportActionBar() is undefined for the type BaseActivity"

修改文件：com.slidingmenu.lib.app.SlidingFragmentActivity.java
public class SlidingFragmentActivity extends FragmentActivity implements SlidingActivityBase
至
public class SlidingFragmentActivity extends SherlockFragmentActivity implements SlidingActivityBase

Then clean and rebuild, this method should now be found.
