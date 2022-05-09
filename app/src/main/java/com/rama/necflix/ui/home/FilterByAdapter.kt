package com.rama.necflix.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.rama.necflix.R
import com.rama.necflix.data.GenresDB
import com.rama.necflix.databinding.ExplistChildBinding
import com.rama.necflix.databinding.ExplistGroupBinding

class FilterByAdapter(
    var genresDB: List<GenresDB>,
    var map: HashMap<String, List<String>>,
    var words: List<String>
) : BaseExpandableListAdapter() {


    override fun getGroupCount(): Int {
        return words.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return map[words[p0]]?.size!!
    }

    override fun getGroup(p0: Int): String {
        return words[p0]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String {
        return map[words[groupPosition]]?.get(childPosition)!!
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isLastChild: Boolean,
        view: View?,
        viewGroup: ViewGroup?
    ): View {
        val itemGroupView: View
        if (view == null) {
            itemGroupView = LayoutInflater.from(viewGroup?.context)
                .inflate(R.layout.explist_group, viewGroup, false)
        } else {
            itemGroupView = view
        }
        val bind = ExplistGroupBinding.bind(itemGroupView)
        bind.group.text = words[groupPosition]
        return itemGroupView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        view: View?,
        parent: ViewGroup?
    ): View {
        val itemChildView: View
        if (view == null) {
            itemChildView =
                LayoutInflater.from(parent?.context).inflate(R.layout.explist_child, parent, false)
        } else {
            itemChildView = view
        }
        val bind = ExplistChildBinding.bind(itemChildView)
        bind.child.text = map[words[groupPosition]]?.get(childPosition)
        return itemChildView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}