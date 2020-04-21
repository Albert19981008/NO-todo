package com.gang.notodo.ui.list


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * ViewPager适配器
 */
class ViewPagerFragmentAdapter(
    fragmentManager: FragmentManager, //Fragment列表
    fragmentList: List<Fragment>
) : FragmentPagerAdapter(fragmentManager) {

    private val mFragmentList = fragmentList

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    /**
     * 得到TabLayout的栏目标题，并把TabLayout和ViewPager适配到一起
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return TITLES[position]
    }

    companion object {

        private val TITLES = arrayOf("未完成", "已完成")
    }
}
