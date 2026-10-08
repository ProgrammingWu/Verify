/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.api.input;

import org.pf4j.ExtensionPoint;

public interface WebFetchInputInterface  extends ExtensionPoint {
    default String preWebFetch(String Body){
        return "";
    }
}
