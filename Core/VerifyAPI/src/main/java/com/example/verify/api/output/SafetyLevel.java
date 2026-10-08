/*
Copyright(c) 2026 Pei Hongzhe. All Rights Reserved.
版权所有(c) 2026 裴鸿喆，保留所有权利。
*/

package com.example.verify.api.output;

public enum SafetyLevel {
    /**未知*/
    Unknown,
    /**绝对安全。将不会调用后续的Out插件来判断是否安全。谨慎使用
     * 正常情况下，应该使用Safe*/
    AbsolutelySafe,
    /**安全*/
    Safe,
    /**危险*/
    unsafe
}
