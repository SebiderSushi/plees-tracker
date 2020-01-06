/*
 * Copyright 2020 Miklos Vajna. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be
 * found in the LICENSE file.
 */

package hu.vmiklos.plees_tracker

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.snackbar.Snackbar

/**
 * This callback handles deletion of one recorded sleep in the sleep list. Listens to left/right swipes only.
 */
class SleepTouchCallback(private val mAdapter: SleepsAdapter)
    : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView,
               viewHolder: RecyclerView.ViewHolder,
               target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                 direction: Int) {
        val dataModel = DataModel.getDataModel()
        val sleep = mAdapter.getData().get(viewHolder.getAdapterPosition())
        dataModel.deleteSleep(sleep)

        val view = viewHolder.itemView
        val snackbar = Snackbar.make(view, R.string.deleted, Snackbar.LENGTH_LONG)
        snackbar.setAction(dataModel.getString(R.string.undo), {
                    dataModel.insertSleep(sleep)
                })

        snackbar.show()
    }

    companion object {
        private val TAG = "SleepTouchCallback"
    }
}