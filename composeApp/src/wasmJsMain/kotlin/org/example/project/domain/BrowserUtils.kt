package org.example.project.domain

import org.w3c.dom.Window

@JsFun("() => window")
external fun getWindow(): Window

fun alert(message: String) {
    getWindow().alert(message)
}

