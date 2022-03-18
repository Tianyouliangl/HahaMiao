package com.xiaomaoyuedan.live.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.goActivity(content: Context, cls: Class<*>?) {
    goActivity(content, cls, null)
}

fun AppCompatActivity.goActivity(content: Context, cls: Class<*>?, bundle: Bundle?) {
    val intent = Intent(content, cls)
    bundle?.let {
        intent.putExtras(it)
    }
    content.startActivity(intent)
}