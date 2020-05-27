package com.gnet.meeting.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gnet.meeting.ui.R

/**
 * <pre>
 *     author : mayu
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/5/11 14:17
 *     desc   : 参会列表
 *     version: 1.0
 * </pre>
 */
class UserListFragment : Fragment() {

    private lateinit var mRootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_user_list, container)
        return mRootView
    }
}